import z from "zod";

export const loginSchema = z.object({
  username: z
    .string()
    .min(5, "Username must be at least 5 characters.")
    .max(32, "Username must be at most 32 characters."),
  password: z
    .string()
    .min(3, "Password must be at least 3 characters.")
    .max(100, "Password must be at most 100 characters."),
})