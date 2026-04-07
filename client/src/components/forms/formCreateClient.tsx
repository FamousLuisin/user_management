import { useForm, Controller, useFieldArray } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { Label } from "../ui/label";
import { Input } from "../ui/input";
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "../ui/select";
import { Button } from "../ui/button";
import { formSchema } from "@/schema/createClientSchema";
import type { ClientRequest, ClientResponse } from "@/types/clientType";
import { fetchAPI } from "@/utils/fetchApi";
import { useAuth } from "@/context/authContex";
import { toast } from "sonner";
import { useState } from "react";
import { Alert, AlertDescription, AlertTitle } from "../ui/alert";
import { InfoIcon } from "lucide-react";
import { API_URL } from "@/config/api";

export function FormCreateClient({ onSuccess }: { onSuccess: () => void }) {
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      name: "",
      cpf: "",
      cep: "",
      phones: [{ number: "", type: "CELULAR" }],
      emails: [{ email: "" }],
    },
  });

  const {
    fields: phoneFields,
    append: addPhone,
    remove: removePhone,
  } = useFieldArray({
    control: form.control,
    name: "phones",
  });

  const {
    fields: emailFields,
    append: addEmail,
    remove: removeEmail,
  } = useFieldArray({
    control: form.control,
    name: "emails",
  });

  const { token } = useAuth();
  const [ err, setErr ] = useState<string | null>(null);

  async function onSubmit(data: z.infer<typeof formSchema>) {
    try {
      const client: ClientRequest = {
        name: data.name,
        cpf: data.cpf,
        cep: data.cep,
        phones: data.phones,
        emails: data.emails
      }

      const response = await fetchAPI<ClientRequest, ClientResponse>(`${API_URL}/api/client`, "POST", {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: client
      })

      if (!response) return

      toast("Event has been created", {
          description: `cliente ${response.name} criado com sucesso`,
          action: {
            label: "Okay",
            onClick: () => console.log("Okay"),
          },
      })

      onSuccess()
    } catch (error) {
      setErr("Erro ao criar um cliente ou falha de autenticação")
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
            <Label>nome</Label>
            <Input {...field} placeholder="Nome" />
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
            <Input {...field} placeholder="CPF" />
            {fieldState.error && <span>{fieldState.error.message}</span>}
          </div>
        )}
      />

      <Controller
        name="cep"
        control={form.control}
        render={({ field, fieldState }) => (
          <div className="flex flex-col gap-2">
            <Label>CEP</Label>
            <Input {...field} placeholder="CEP" />
            {fieldState.error && <span>{fieldState.error.message}</span>}
          </div>
        )}
      />

      <div className="flex flex-col gap-2">
        <Label>Telefone</Label>

        {phoneFields.map((item, index) => (
          <div key={item.id} className="flex gap-2">
            <Controller
              name={`phones.${index}.number`}
              control={form.control}
              render={({ field }) => (
                <Input {...field} placeholder="Número" />
              )}
            />

            <Controller
              name={`phones.${index}.type`}
              control={form.control}
              render={({ field }) => (
                <Select
                onValueChange={field.onChange}
                defaultValue={field.value}
                >
                    <SelectTrigger className="w-full max-w-48">
                        <SelectValue placeholder="Selecione o tipo" />
                    </SelectTrigger>

                    <SelectContent>
                        <SelectGroup>
                        <SelectLabel>Tipos</SelectLabel>

                        <SelectItem value="CELULAR">
                            Celular
                        </SelectItem>

                        <SelectItem value="RESIDENCIAL">
                            Residencial
                        </SelectItem>

                        <SelectItem value="COMERCIAL">
                            Comercial
                        </SelectItem>

                        </SelectGroup>
                    </SelectContent>
                </Select>
                )}
            />

            <Button variant={"destructive"} className="cursor-pointer" onClick={() => removePhone(index)}>
              Remover
            </Button>
          </div>
        ))}

        <Button className="cursor-pointer"
          onClick={() => addPhone({ number: "", type: "CELULAR" })}
        >
          + Adicionar telefone
        </Button>
      </div>

      <div className="flex flex-col gap-2">
        <Label>Emails</Label>

        {emailFields.map((item, index) => (
          <div key={item.id} className="flex gap-2">
            <Controller
              name={`emails.${index}.email`}
              control={form.control}
              render={({ field }) => (
                <Input {...field} placeholder="Email" />
              )}
            />

            <Button className="cursor-pointer" variant={"destructive"} onClick={() => removeEmail(index)}>
              Remover
            </Button>
          </div>
        ))}

        <Button
          className="cursor-pointer"
          onClick={() => addEmail({ email: "" })}
        >
          + Adicionar email
        </Button>
      </div>

      {err && (
          <Alert variant="destructive">
            <InfoIcon />
            <AlertTitle>Warning</AlertTitle>
            <AlertDescription>
              {err}
            </AlertDescription>
          </Alert>
      )}

      <Button className="cursor-pointer" type="submit">Salvar</Button>
    </form>
  );
}