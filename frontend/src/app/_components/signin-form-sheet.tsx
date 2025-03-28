'use client'

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
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
import FormInputText from "./fomr-input-text";

interface Props {
    trigger: React.ReactNode;
}

export default function SignInFormSheet({ trigger }: Props) {
    const hookFormMethods = useForm<SignInDto>();

    const submit = async (data: SignInDto) => {
        console.log(data)
    }

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
                        <Button>Sign in</Button>
                    </div>
                </Form>
            </SheetContent>
        </Sheet>
    )
}