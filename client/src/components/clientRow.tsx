import type { ClientResponse } from "@/types/clientType";
import { TableCell, TableRow } from "./ui/table";
import type React from "react";
import { DropMenuClient } from "./dropMenuClient";
import { useState } from "react";
import { useAuth } from "@/context/authContex";

export function ClientRow({ c, ref, onDelete }: { c: ClientResponse; ref?: React.Ref<HTMLTableRowElement>; onDelete: (client: ClientResponse) => void}) {
  const [ client, setClient ] = useState<ClientResponse>(c)
  const { user } = useAuth();

  const emailList = client.emails?.map(e => e.email).join(", ") || "-";
  const phoneList = client.phones?.map(p => p.number).join(", ") || "-";

  const fullAddress = client.address
    ? `${client.address.logradouro}, ${client.address.bairro}, ${client.address.cidade} - ${client.address.uf}`
    : "-";

  return (
    <TableRow ref={ref}>
      <TableCell>
        <div>{client.name}</div>
        <div>{client.cpf}</div>
      </TableCell>
      <TableCell>
        <div>
          {client.address.cep}
        </div>
        {fullAddress}
      </TableCell>
      <TableCell>
        <div>
          {emailList}
        </div>
        <div>
          {phoneList}
        </div>
      </TableCell>
      { user?.roles.includes("ADMIN") && (
        <TableCell>
          <DropMenuClient setClient={setClient} onDelete={onDelete} client={client}/>
        </TableCell>
      )}
    </TableRow>
  );
}