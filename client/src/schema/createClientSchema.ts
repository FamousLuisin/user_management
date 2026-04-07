import z from "zod";

const phoneSchema = z.object({
  number: z.string().min(8, "Número inválido"),
  type: z.enum(["CELULAR", "RESIDENCIAL", "COMERCIAL"]),
});

const emailSchema = z.object({
  email: z.string().email("Email inválido"),
});

export const formSchema = z.object({
  name: z.string().min(3, "Nome obrigatório"),
  cpf: z.string().min(11, "CPF inválido"),
  cep: z.string().min(8, "CEP inválido"),
  phones: z.array(phoneSchema).min(1, "Adicione ao menos um telefone"),
  emails: z.array(emailSchema).min(1, "Adicione ao menos um email"),
});