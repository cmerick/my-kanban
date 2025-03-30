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
import { Loader2 } from "lucide-react";
import usePreventAsyncFunction from "../_helpers/prevent-async-function.hook";
import { signIn } from "@/services/authentication/auth.service";
interface Props {
    trigger: React.ReactNode;
}

export default function SignInFormSheet({ trigger }: Props) {
    const hookFormMethods = useForm<SignInDto>();

    const _submit = async (data: SignInDto) => {
        try {
            await signIn(data)
        } catch (errors: any) {
            console.error(errors)
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
                        <FormInputText className="" placeholder="username" autoComplete='username' type="text" name="username" />
                        <FormInputText placeholder="password" autoComplete='password' type="password" name="password" />
                        <Link href={''}> <small>Forgot my password</small> </Link>
                        <Button>{!isUnlock && <Loader2 className="animate-spin" visibility={!isUnlock ? 'visible' : 'hidden'} />} Sign in</Button>
                    </div>
                </Form>
            </SheetContent>
        </Sheet>
    )
}