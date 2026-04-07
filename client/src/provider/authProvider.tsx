import { useState, useEffect, type ReactNode } from "react";
import { jwtDecode } from "jwt-decode";
import { AuthContext } from "@/context/authContex";
import type { AuthType, AuthUser, LoginRequest } from "@/types/authType";
import { fetchAPI } from "@/utils/fetchApi";
import { API_URL } from "@/config/api";

type Props = {
  children: ReactNode;
};

type JwtPayload = {
  sub: string;
  roles: ("ADMIN" | "COMMON")[];
};

export function AuthProvider({ children }: Props) {
  const [token, setToken] = useState<string | null>(null);
  const [user, setUser] = useState<AuthUser | null>(null);

  const isAuthenticated = !!token;

  function setAuthFromToken(token: string) {
    const decoded = jwtDecode<JwtPayload>(token);

    const user: AuthUser = {
      username: decoded.sub,
      roles: decoded.roles,
    };

    setToken(token);
    setUser(user);
  }

  async function login(username: string, password: string): Promise<void> {
    try {
      const login: LoginRequest = { username, password };
      console.log(API_URL)
      const data = await fetchAPI<LoginRequest, { token: string }>(`${API_URL}/auth/login`, "POST", {
        body: login,
      });

      if (!data) throw Error("erro ao obter token")

      const token = data.token;
      setAuthFromToken(token);
      localStorage.setItem("token", token);
    } catch (error) {
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