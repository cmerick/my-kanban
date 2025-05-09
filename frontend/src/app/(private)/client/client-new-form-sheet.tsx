'use client'

import { Button } from "@/components/ui/button";
import {
    Sheet,
    SheetContent,
    SheetHeader,
    SheetTitle,
    SheetTrigger,
} from "@/components/ui/sheet"
import { useForm } from "react-hook-form";
import { Loader2 } from "lucide-react";
import { ClientRequestDto, ClientRequestSchema } from "@/app/_models/client/client-request.dto";
import usePreventAsyncFunction from "@/app/_helpers/prevent-async-function.hook";
import Form from "@/app/_components/form";
import FormInputText from "@/app/_components/form-input-text";
import { useEffect, useState } from "react";
import ClientResponseDto from "@/app/_models/client/client-response.dto";
import { frontendApi } from "@/configuration/api-config";
interface Props {
    icon?: React.ReactNode;
    triggerLabel?: string;
    onClientCreate: () => Promise<void>,
    client?: ClientResponseDto | null;
}

export default function ClientNewFormSheet({ icon, triggerLabel, onClientCreate, client }: Props) {
    const hookFormMethods = useForm<ClientRequestDto>({ defaultValues: client ? { name: client?.name } : undefined });
    const [open, setOpen] = useState(false);
    useEffect(() => {
        if (client && !open) {
            hookFormMethods.reset({ name: client.name });
            setOpen(true);
        } else {
            hookFormMethods.reset({ name: "" });
        }
    }, [client]);

    const _submit = async (data: ClientRequestDto) => {
        try {
            if (client) {
                await frontendApi.put(`/clients/${client.id}`, data);
            } else {
                await frontendApi.post('/clients', data);
            }
            await onClientCreate();
            setOpen(false);

        } catch (errors: any) {
            console.error(errors)
        }
    }

    const { safeFunction: submit, isUnlock } = usePreventAsyncFunction(_submit);



    return (
        <Sheet open={open} onOpenChange={setOpen}>
            {triggerLabel || icon ? (
                <SheetTrigger className="inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-md text-sm font-medium transition-all disabled:pointer-events-none disabled:opacity-50 [&_svg]:pointer-events-none [&_svg:not([class*='size-'])]:size-4 shrink-0 [&_svg]:shrink-0 outline-none focus-visible:border-ring focus-visible:ring-ring/50 focus-visible:ring-[3px] aria-invalid:ring-destructive/20 dark:aria-invalid:ring-destructive/40 aria-invalid:border-destructive bg-primary text-primary-foreground shadow-xs hover:bg-primary/90 h-9 px-4 py-2 has-[>svg]:px-3">
                    <div>{icon}</div>
                    {triggerLabel}
                </SheetTrigger>
            ) : null}
            <SheetContent>
                <Form
                    onSubmit={submit}
                    hookFormMethods={hookFormMethods}
                    schema={ClientRequestSchema}
                >
                    <SheetHeader>
                        <SheetTitle>{client ? 'Edit Client' : 'New Client'}</SheetTitle>
                    </SheetHeader>
                    <div className="flex flex-col gap-2 mx-8 ">
                        <FormInputText className="" placeholder="name" autoComplete='name' type="text" name="name" />
                        <Button>{!isUnlock && <Loader2 className="animate-spin" visibility={!isUnlock ? 'visible' : 'hidden'} type="submit" />} {client ? 'Save' : 'Create'}</Button>
                    </div>
                </Form>
            </SheetContent>
        </Sheet>
    )
}