import type { AuthType } from "@/types/authType";
import { createContext, useContext } from "react";

export const AuthContext = createContext<AuthType | undefined>(undefined);


export function useAuth() {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth must be used within AuthProvider");
  }

  return context;
}