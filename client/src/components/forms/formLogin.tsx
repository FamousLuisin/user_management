import { loginSchema } from "@/schema/loginSchema";
import { zodResolver } from "@hookform/resolvers/zod"
import { Controller, useForm } from "react-hook-form";
import type z from "zod";
import { FieldGroup } from "../ui/field";
import { Input } from "../ui/input";
import { useAuth } from "@/context/authContex";
import { useState } from "react";
import { Button } from "../ui/button";
import { Label } from "../ui/label";
import { Alert, AlertDescription, AlertTitle } from "../ui/alert";
import { InfoIcon } from "lucide-react";

export function FormLogin() {
  const form = useForm<z.infer<typeof loginSchema>>({ 
    resolver: zodResolver(loginSchema),
    defaultValues: {
      username: "",
      password: "",
    },
  }); 

  const [ err, setErr ] = useState<string | null>(null);

  const { login } = useAuth();

  async function onSubmit(data: z.infer<typeof loginSchema>) {
    try {
      await login(data.username, data.password);
    } catch (error) {
      setErr("credenciais invalidas");
    }
  }

  return (
    <div>
      <form className="flex flex-col gap-4" onSubmit={form.handleSubmit(onSubmit)}>
        <FieldGroup>
          <Controller
            name="username"
            control={form.control}
            render={({ field, fieldState }) => (
              <div className="flex flex-col gap-2">
                <Label htmlFor="username">Username</Label>
                <Input {...field} placeholder="Username" />
                {fieldState.error && <span className="text-red-500">{fieldState.error.message}</span>}
              </div>
            )}
          />

          <Controller
            name="password"
            control={form.control}
            render={({ field, fieldState }) => (
              <div className="flex flex-col gap-2">
                <Label htmlFor="password">Password</Label>
                <Input {...field} type="password" placeholder="Password" />
                {fieldState.error && <span className="text-red-500">{fieldState.error.message}</span>}
              </div>
            )}
          />
        </FieldGroup>

        {err && (
          <Alert variant="destructive">
            <InfoIcon />
            <AlertTitle>Warning</AlertTitle>
            <AlertDescription>
              {err}
            </AlertDescription>
          </Alert>
        )}

        <Button className="cursor-pointer" type="submit">Login</Button>
      </form>

    </div>
  );
}