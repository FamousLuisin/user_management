type Role = "ADMIN" | "COMMON";

export type AuthUser = {
  username: string;
  role: Role;
};

export type AuthType = {
    token: string | null;
    user: AuthUser | null;
    isAuthenticated: boolean;
    login: (username: string, password: string) => Promise<void>;
    logout: () => void;
}
