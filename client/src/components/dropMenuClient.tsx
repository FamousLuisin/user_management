import { MoreHorizontal } from "lucide-react";
import { Button } from "./ui/button";
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "./ui/dropdown-menu";
import type { ClientResponse } from "@/types/clientType";
import { fetchAPI } from "@/utils/fetchApi";
import { useAuth } from "@/context/authContex";
import { toast } from "sonner";
import { DialogFormUpdateClient } from "./dialogFormUpdateClient";
import { useState } from "react";
import { API_URL } from "@/config/api";

export function DropMenuClient({
  client,
  onDelete,
  setClient
}: {
  client: ClientResponse;
  onDelete: (cliente: ClientResponse) => void;
  setClient: React.Dispatch<React.SetStateAction<ClientResponse>>
}) {
  const { token } = useAuth();
  const [open, setOpen] = useState(false);

  async function deleteClient(client: ClientResponse) {
    try {
      await fetchAPI<null>(
        `${API_URL}/api/client/${client.id}`,
        "DELETE",
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      onDelete(client);
    } catch (error) {
      toast("Erro ao deletar client", {
        description: `erro ao deleta ou falha de autenticação`,
        
      });
    }
  }

  return (
    <>
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <Button variant="ghost" className="h-8 w-8 p-0 cursor-pointer">
            <MoreHorizontal className="h-4 w-4" />
          </Button>
        </DropdownMenuTrigger>

        <DropdownMenuContent align="end">
          <DropdownMenuLabel>Actions</DropdownMenuLabel>
          <DropdownMenuSeparator />

          <DropdownMenuItem
            className="cursor-pointer"
            onClick={(e) => {
              e.preventDefault();
              setOpen(true);
            }}
          >
            Update
          </DropdownMenuItem>

          <DropdownMenuItem
            className="cursor-pointer"
            onClick={() => deleteClient(client)}
          >
            Delete
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>

      <DialogFormUpdateClient
        client={client}
        open={open}
        onOpenChange={setOpen}
        setClient={setClient}
      />
    </>
  );
}