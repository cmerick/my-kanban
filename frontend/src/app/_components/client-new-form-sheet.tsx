'use client'

import { Button } from "@/components/ui/button";
import {
    Sheet,
    SheetContent,
    SheetHeader,
    SheetTitle,
    SheetTrigger,
} from "@/components/ui/sheet"
import Link from "next/link";
import { useForm } from "react-hook-form";
import Form from "./form";
import { SignInDto, SignInSchema } from "../_models/authentication/signin.dto";
import FormInputText from "./form-input-text";
import { signIn } from "../_services/authentication/auth.service";
import { Loader2 } from "lucide-react";
import usePreventAsyncFunction from "../_helpers/prevent-async-function.hook";
import { clientCreate } from "../_services/client/client.service";
import { ClientRequestDto } from "../_models/client/client-request.dto";
interface Props {
    trigger: React.ReactNode;
}

export default function ClientNewFormSheet({ trigger }: Props) {
    const hookFormMethods = useForm<ClientRequestDto>();

    const _submit = async (data: ClientRequestDto) => {
        try {
            await clientCreate(data)
        } catch (errors: any) {
            console.log(errors)
        }
    }

    const { safeFunction: submit, isUnlock } = usePreventAsyncFunction(_submit);

    return (
        <Sheet>
            <SheetTrigger >{trigger}</SheetTrigger>
            <SheetContent>
                <Form
                    onSubmit={submit}
                    hookFormMethods={hookFormMethods}
                    schema={SignInSchema}
                >
                    <SheetHeader>
                        <SheetTitle>Access your account</SheetTitle>
                    </SheetHeader>
                    <div className="flex flex-col gap-2 mx-8 ">
                        <FormInputText className="" placeholder="name" autoComplete='name' type="text" name="name" />
                        <Button>{!isUnlock && <Loader2 className="animate-spin" visibility={!isUnlock ? 'visible' : 'hidden'} />} Create</Button>
                    </div>
                </Form>
            </SheetContent>
        </Sheet>
    )
}