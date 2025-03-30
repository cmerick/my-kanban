'use client'
import { Button } from "@/components/ui/button";
import { signOut } from "@/services/authentication/auth.service";
import Link from "next/link";

export function Header() {
    return (
        <header className="flex justify-between items-center mb-4">
            <h1 className="">Project Manager</h1>
            <div className=" flex gap-2 items-center">
                <Link href={'/client'}>Client</Link>
                <Link href={'/project'}>Project</Link>
                <Link href={'/task'}>Task</Link>
                <Button onClick={() => signOut()}>Sign out</Button>
            </div>
        </header>
    )
}