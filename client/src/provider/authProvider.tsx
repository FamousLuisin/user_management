import { useState, useEffect, type ReactNode } from "react";
import { jwtDecode } from "jwt-decode";
import { AuthContext } from "@/context/authContex";
import type { AuthType, AuthUser } from "@/types/authType";

type Props = {
  children: ReactNode;
};

type JwtPayload = {
  sub: string;
  role: "ADMIN" | "COMMON";
};

export function AuthProvider({ children }: Props) {
  const [token, setToken] = useState<string | null>(null);
  const [user, setUser] = useState<AuthUser | null>(null);

  const isAuthenticated = !!token;

  function login(token: string) {
    const decoded = jwtDecode<JwtPayload>(token);

    const user: AuthUser = {
      username: decoded.sub,
      role: decoded.role,
    };

    setToken(token);
    setUser(user);

    localStorage.setItem("token", token);
  }

  function logout() {
    setToken(null);
    setUser(null);
    localStorage.removeItem("token");
  }

  useEffect(() => {
    const storedToken = localStorage.getItem("token");

    if (storedToken) {
      login(storedToken);
    }
  }, []);

  const value: AuthType = {
    token,
    user,
    isAuthenticated,
    login,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}