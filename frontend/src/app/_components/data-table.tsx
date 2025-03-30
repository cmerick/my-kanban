'use client'
import {
    Table,
    TableBody,
    TableCaption,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table"
import { flexRender, getCoreRowModel, useReactTable } from "@tanstack/react-table"
import { createColumns } from "../_util/create-columns";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { MoreHorizontal } from "lucide-react";
import { Button } from "@/components/ui/button";

export interface TableAction<TData> {
    name: string;
    method: (id: string | number, row: TData) => Promise<void>;
}

interface DataTableProps<TData, TValue> {
    data: TData[];
    columnKeys: (keyof TData & string)[];
    paginator?: boolean;
    rowsPerPageOptions?: number[];
    actions?: TableAction<TData>[];
    idKey: keyof TData;
}

export function DataTable<TData>({ data, columnKeys, actions, idKey }: DataTableProps<TData, any>) {
    let columns = createColumns<TData>(columnKeys);

    if (actions && actions.length > 0) {
        columns = [
            ...columns,
            {
                id: "actions",
                header: "Actions",
                cell: ({ row }) => {
                    const rowData = row.original;
                    const itemId = rowData[idKey] as string | number;

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
                                        onClick={() => action.method(itemId, rowData)}
                                    >
                                        {action.name}
                                    </DropdownMenuItem>
                                ))}
                                <DropdownMenuSeparator />
                            </DropdownMenuContent>
                        </DropdownMenu >
                    );
                }
            },
        ];
    }

    const table = useReactTable({
        data,
        columns,
        getCoreRowModel: getCoreRowModel(),
    });

    return (
        <div>
            <Table>
                <TableCaption>{data.length} record(s) found</TableCaption>
                <TableHeader>
                    {table.getHeaderGroups().map((headerGroup) => (
                        <TableRow key={headerGroup.id}>
                            {headerGroup.headers.map((header) => (
                                <TableHead key={header.id}>
                                    {header.isPlaceholder
                                        ? null
                                        : flexRender(header.column.columnDef.header, header.getContext())}
                                </TableHead>
                            ))}
                        </TableRow>
                    ))}
                </TableHeader>
                <TableBody>
                    {table.getRowModel().rows?.length ? (
                        table.getRowModel().rows.map((row) => (
                            <TableRow key={row.id} data-state={row.getIsSelected() && "selected"}>
                                {row.getVisibleCells().map((cell) => (
                                    <TableCell key={cell.id}>
                                        {flexRender(cell.column.columnDef.cell, cell.getContext())}
                                    </TableCell>
                                ))}
                            </TableRow>
                        ))
                    ) : (
                        <TableRow>
                            <TableCell colSpan={columns.length} className="h-24 text-center">
                                No results
                            </TableCell>
                        </TableRow>
                    )}
                </TableBody>
            </Table>
        </div>
    );
}
