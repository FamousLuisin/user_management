import { FormLogin } from "./forms/formLogin";
import { Button } from "./ui/button";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "./ui/dialog";

export default function DialogFormLogin() {
  return (
      <Dialog>
          <DialogTrigger asChild>
            <Button
              className="cursor-pointer transition-all duration-200 hover:scale-105 active:scale-95"
            >
              Login
            </Button>
          </DialogTrigger>
          <DialogContent className="sm:max-w-sm">
            <div className="flex flex-col items-start gap-4 w-full">
              <DialogHeader className="border-b w-full pb-2">
                <DialogTitle>Login</DialogTitle>
              </DialogHeader>
            
              <div className="w-full">
                <FormLogin />
              </div>
            </div>
          </DialogContent>
      </Dialog>
  )
}