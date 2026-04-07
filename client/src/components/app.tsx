import { useAuth } from '@/context/authContex'
import DialogFormLogin from './dialogFormLogin'
import { ClientsTable } from './clientsTable'
import { DialogFormCreateClient } from './dialogFormCreateClient'

function App() {
  const { isAuthenticated, user } = useAuth()

  if (!isAuthenticated) {
    return (
      <div className="w-10/12 flex flex-col items-center justify-center bg-card rounded-md shadow-md h-[calc(100vh-6rem)]">
        <div className="flex flex-col items-center justify-center gap-4 py-10 text-center">
          <h2 className="text-2xl font-semibold">
            Bem-vindo ao ClientFlow 👋
          </h2>

        <p className="text-muted-foreground max-w-md">
          Este é um sistema para gerenciamento de clientes, onde você pode
          visualizar, cadastrar e organizar seus dados de forma simples e eficiente.
        </p>

        <DialogFormLogin />
      </div>
      
    </div>
    )
  }

  return (
    <div className="w-10/12 flex flex-col items-center bg-card rounded-md shadow-md p-3 gap-2 h-[calc(100vh-6rem)]">
      <h1 className="text-2xl font-semibold tracking-tight">
        Clientes
      </h1>
      { user?.roles.includes("ADMIN") &&(
        <DialogFormCreateClient />
      )}
      <ClientsTable />
    </div>
  )
}
export default App
