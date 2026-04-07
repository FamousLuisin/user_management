import { z } from "zod";

export const clientUpdateSchema = z.object({
  name: z
    .string()
    .min(3, "Nome deve ter pelo menos 3 caracteres"),

  cpf: z
    .string()
    .min(11, "CPF inválido")
    .max(14, "CPF inválido"),
});