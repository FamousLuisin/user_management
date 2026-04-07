import logo from "@/assets/logo.png";
import { Button } from "./ui/button";
import { useAuth } from "@/context/authContex";
import DialogFormLogin from "./dialogFormLogin";

function Header() {
  const { isAuthenticated, logout } = useAuth();

  return (
    <header className="w-10/12 flex items-center justify-between py-3 px-2 rounded-b-md shadow-md bg-card">
      <div className="flex items-center space-x-2">
        <img src={logo} alt="Logo" className="h-10 w-10" />
        <h1 className="text-2xl font-bold">User Management</h1>
      </div>
      <div>
        {isAuthenticated ? (
          <Button className="cursor-pointer" onClick={logout}>
            Logout
          </Button>
        ) : (
          <DialogFormLogin />
        )}
      </div>
    </header>
  );
}

export default Header;