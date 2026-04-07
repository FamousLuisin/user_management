import { Outlet } from "react-router";
import Header from "./header";

function Layout() {
  return (
    <div className="min-h-screen flex flex-col items-center bg-background">
        <Header />
        <Outlet />
    </div>
  );
}

export default Layout;