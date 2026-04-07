import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './components/app'
import { createBrowserRouter, RouterProvider } from 'react-router'
import Layout from './components/layout'
import { AuthProvider } from './provider/authProvider'
import { Toaster } from 'sonner'

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        path: "/",
        element: <App />,
      },
    ],

  },
]);

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthProvider>
      <Toaster position='top-center'/>
      <RouterProvider router={router} />
    </AuthProvider>
  </StrictMode>,
)
