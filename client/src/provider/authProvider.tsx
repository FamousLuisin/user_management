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

  function setAuthFromToken(token: string) {
    const decoded = jwtDecode<JwtPayload>(token);

    const user: AuthUser = {
      username: decoded.sub,
      role: decoded.role,
    };

    setToken(token);
    setUser(user);
  }

  async function login(username: string, password: string): Promise<void> {
    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        throw new Error("Invalid credentials");
      }

      const data = await response.json();
      const token = data.token;

      setAuthFromToken(token);
      localStorage.setItem("token", token);
    } catch (error) {
      console.error("Login error:", error);
      throw error;
    }
  }

  function logout() {
    setToken(null);
    setUser(null);
    localStorage.removeItem("token");
  }

  useEffect(() => {
    const storedToken = localStorage.getItem("token");

    if (storedToken) {
      setAuthFromToken(storedToken);
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