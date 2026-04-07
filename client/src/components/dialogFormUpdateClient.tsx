import { Dialog, DialogContent, DialogHeader, DialogTitle } from "./ui/dialog";
import { FormUpdateClient } from "./forms/formUpdateClient";
import type { ClientResponse } from "@/types/clientType";

export function DialogFormUpdateClient({
  client,
  open,
  onOpenChange,
  setClient
}: {
  client: ClientResponse;
  open: boolean;
  onOpenChange: (open: boolean) => void;
  setClient: React.Dispatch<React.SetStateAction<ClientResponse>>
}) {
  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-sm">
        <div className="flex flex-col items-start gap-4 w-full">
          <DialogHeader className="border-b w-full pb-2">
            <DialogTitle>Update client</DialogTitle>
          </DialogHeader>

          <div className="w-full">
            <FormUpdateClient
              client={client}
              onSuccess={() => onOpenChange(false)}
              setClient={setClient}
            />
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
}