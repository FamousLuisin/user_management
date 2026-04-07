import {
  Table,
  TableBody,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import type { ClientResponse } from "@/types/clientType"
import { useCallback, useEffect, useRef, useState } from "react"
import { ClientRow } from "./clientRow"
import { fetchAPI } from "@/utils/fetchApi"
import { toast } from "sonner"
import { API_URL } from "@/config/api"

export function ClientsTable() {

	const [clients, setClients] = useState<ClientResponse[]>([]);
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const [isLoading, setIsLoading] = useState(false);
  
  const isFetching = useRef(false); 
  const observer = useRef<IntersectionObserver | null>(null);
  const token = localStorage.getItem("token");

  const fetchClients = useCallback(async (pageToFetch: number) => {
    if (isFetching.current || !hasMore) return;

    isFetching.current = true;
    setIsLoading(true);

    try {
      console.log("CARREGANDO PAGINA " + pageToFetch)
      const data = await fetchAPI<unknown, { content: ClientResponse[], totalPages: number, page: number }>(
        `${API_URL}/api/client?page=${pageToFetch}&size=10`,
        "GET",
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          }
        }
      );

      if (!data) return;

      const newClients = data.content || [];
      
      setClients(prev => [...prev, ...(data.content || [])]);

      if (data.page + 1 >= data.totalPages || newClients.length === 0) {
        setHasMore(false);
      }
    } catch (error) {
      console.error("Error fetching clients:", error);
    } finally {
      setIsLoading(false);
      isFetching.current = false;
    }
  }, [token, hasMore]);

  useEffect(() => {
    fetchClients(page);
  }, [page, fetchClients]);

  const onDelete = (client: ClientResponse) => {
    setClients(prev =>
      prev.filter(c => c.id !== client.id)
    );

    toast("Event has been created", {
          description: `cliente ${client.name} deletado com sucesso`,
      })
  };

  const lastClientRef = useCallback((node: HTMLTableRowElement | null) => {
    if (isLoading || !hasMore) return;
    
    if (observer.current) observer.current.disconnect();

    observer.current = new IntersectionObserver(entries => {
      if (entries[0].isIntersecting && !isFetching.current) {
        setPage(prev => prev + 1);
      }
    });

    if (node) observer.current.observe(node);
  }, [isLoading, hasMore]);

  return (
    <div className="w-10/12 flex-1 border rounded-md overflow-auto max-h-150">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Client</TableHead>
            <TableHead>Address</TableHead>
            <TableHead>Contact</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {clients.map((client, index) => (
            <ClientRow
              key={`${client.id}-${index}`}
              c={client}
              onDelete={onDelete}
              ref={index === clients.length - 1 ? lastClientRef : undefined}
            />
          ))}
          {isLoading && (
             <TableRow>
               <td colSpan={3} className="text-center p-4 italic text-muted-foreground">
                 Carregando mais clientes...
               </td>
             </TableRow>
          )}
        </TableBody>
      </Table>
    </div>
  );
}