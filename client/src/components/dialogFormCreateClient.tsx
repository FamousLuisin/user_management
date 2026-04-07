import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "./ui/dialog";
import { Button } from "./ui/button";
import { BadgePlus } from "lucide-react";
import { FormCreateClient } from "./forms/formCreateClient";
import { useState } from "react";

export function DialogFormCreateClient(){
    const [open, setOpen] = useState(false)

    return (
        <Dialog open={open} onOpenChange={setOpen}>
            <DialogTrigger asChild>
                <Button
                className="cursor-pointer transition-all duration-200 hover:scale-105 active:scale-95"
                >
                <BadgePlus /> add client
                </Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-sm">
                <div className="flex flex-col items-start gap-4 w-full">
                <DialogHeader className="border-b w-full pb-2">
                    <DialogTitle>create client</DialogTitle>
                </DialogHeader>
                
                <div className="w-full">
                    <FormCreateClient onSuccess={() => setOpen(false)} />
                </div>
                </div>
            </DialogContent>
        </Dialog>
    )
}