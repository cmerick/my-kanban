'use client'
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Button } from "@/components/ui/button";
import { MoreHorizontal } from "lucide-react";
import { TableAction } from "./data-table";

interface TableActionsProps<TData> {
    row: TData;
    actions: TableAction<TData>[];
    idKey: keyof TData;
}

export function TableActions<TData>({ row, actions, idKey }: TableActionsProps<TData>) {
    const itemId = row[idKey] as string | number;

    return (
        <DropdownMenu>
            <DropdownMenuTrigger asChild>
                <Button variant="ghost" className="h-8 w-8 p-0">
                    <span className="sr-only">Open menu</span>
                    <MoreHorizontal className="h-4 w-4" />
                </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
                <DropdownMenuLabel>Actions</DropdownMenuLabel>
                {actions.map((action, index) => (
                    <DropdownMenuItem
                        key={index}
                        onClick={() => action.method(itemId, row)}
                    >
                        {action.name}
                    </DropdownMenuItem>
                ))}
                <DropdownMenuSeparator />
            </DropdownMenuContent>
        </DropdownMenu>
    );
}
