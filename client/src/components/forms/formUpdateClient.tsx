import { useForm, Controller } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useState } from "react";
import { clientUpdateSchema } from "@/schema/updateCLienteSchema";
import { z } from "zod";
import { useAuth } from "@/context/authContex";
import type { ClienteUpdate, ClientResponse } from "@/types/clientType";
import { fetchAPI } from "@/utils/fetchApi";
import { toast } from "sonner";
import { Input } from "../ui/input";
import { Label } from "../ui/label";
import { Alert, AlertDescription, AlertTitle } from "../ui/alert";
import { Button } from "../ui/button";
import { API_URL } from "@/config/api";

export function FormUpdateClient({
  client,
  onSuccess,
  setClient
}: {
  client: ClientResponse;
  onSuccess: () => void;
  setClient: React.Dispatch<React.SetStateAction<ClientResponse>>
}) {
  const form = useForm<z.infer<typeof clientUpdateSchema>>({
    resolver: zodResolver(clientUpdateSchema),
    defaultValues: {
      name: client.name,
      cpf: client.cpf,
    },
  });

  const { token } = useAuth();
  const [err, setErr] = useState<string | null>(null);

  async function onSubmit(data: z.infer<typeof clientUpdateSchema>) {
    try {
      const clientUpdate: ClienteUpdate = {
        name: data.name,
        cpf: data.cpf,
      };

      const response = await fetchAPI<ClienteUpdate, ClientResponse>(
        `${API_URL}/api/client/${client.id}`,
        "PUT",
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: clientUpdate,
        }
      );

      if (!response) return;

      toast("Cliente atualizado", {
        description: `Cliente ${clientUpdate.name} atualizado com sucesso`,
      });

      client.name = response.name
      client.cpf = response.cpf
      setClient({...client, name: response.name, cpf: response.cpf})
      onSuccess()
    } catch (error) {
      setErr("Erro ao atualizar cliente ou falha de autenticação");
    }
  }

  return (
    <form
      className="flex flex-col gap-4"
      onSubmit={form.handleSubmit(onSubmit)}
    >
      <Controller
        name="name"
        control={form.control}
        render={({ field, fieldState }) => (
          <div className="flex flex-col gap-2">
            <Label>Nome</Label>
            <Input {...field} placeholder="Nome"/>
            {fieldState.error && <span>{fieldState.error.message}</span>}
          </div>
        )}
      />

      <Controller
        name="cpf"
        control={form.control}
        render={({ field, fieldState }) => (
          <div className="flex flex-col gap-2">
            <Label>CPF</Label>
            <Input {...field} placeholder="CPF"/>
            {fieldState.error && <span>{fieldState.error.message}</span>}
          </div>
        )}
      />

      {err && (
        <Alert variant="destructive">
          <AlertTitle>Erro</AlertTitle>
          <AlertDescription>{err}</AlertDescription>
        </Alert>
      )}

      <Button className="cursor-pointer" type="submit">
        Atualizar
      </Button>
    </form>
  );
}