'use client'
import { DataTable } from "@/app/_components/data-table"
import Form from "@/app/_components/form";
import usePreventAsyncFunction from "@/app/_helpers/prevent-async-function.hook";
import ClientResponseDto from "@/app/_models/client/client-response.dto";
import { Button } from "@/components/ui/button";
import { FolderSymlink, Loader2, Plus, Search } from "lucide-react"
import { useState } from "react";
import { useForm } from "react-hook-form";
import ClientNewFormSheet from "./client-new-form-sheet";
import SimpleStatusEnum, { parserSimpleStatus } from "@/app/_models/enum/simple-status.enum";
import { TableActions } from "@/app/_components/data-table-action";
import Link from "next/link";
import { frontendApi } from "@/configuration/api-config";
import { mapperList } from "@/app/_util/mapper.util";

export default function ClientPageComponent() {
    const [values, setValues] = useState<ClientResponseDto[]>([]);
    const [editingClient, setEditingClient] = useState<ClientResponseDto | null>(null);
    const hookFormMethods = useForm<ClientResponseDto>();

    const _search = async () => {
        const response = await frontendApi.get('/clients')

        setValues(mapperList(ClientResponseDto, response.data))
    }

    const handleActiveInactive = async (id: string) => {
        await frontendApi.delete(`clients/${id}/toggle-status`);
        await _search();
    }

    const handleEdit = async (entity: ClientResponseDto) => {
        setEditingClient(entity);
    }

    const handleClientEdit = async () => {
        setEditingClient(null);
        await _search();
    }
    const { safeFunction: search, isUnlock } = usePreventAsyncFunction(_search);

    const columns = (values: ClientResponseDto[]) => [
        {
            accessorKey: "",
            header: "More",
            cell: (info: any) => <Link href={'/'/* () => console.log(info.row.original.id) */}><FolderSymlink size={20} /></Link>,
        },
        {
            accessorKey: "id",
            header: "#",
            cell: (info: any) => info.getValue(),
        },
        {
            accessorKey: "name",
            header: "Name",
            cell: (info: any) => info.getValue(),
        },
        {
            accessorKey: "updatedAt",
            header: "Updated At",
            cell: (info: any) => new Date(`${info.getValue()}`).toLocaleString(),
        },
        {
            accessorKey: "createdAt",
            header: "Created At",
            cell: (info: any) => new Date(`${info.getValue()}`).toLocaleString(),
        },
        {
            accessorKey: "status",
            header: "Status",
            cell: (info: any) => `${parserSimpleStatus(`${info.getValue()}` as SimpleStatusEnum)}`,
        },
    ];

    const actions = [
        {
            name: 'Edit',
            method: async (_id: string, row: ClientResponseDto) => handleEdit(row)

        },
        {
            name: 'Change Status',
            method: async (id: string, _row: ClientResponseDto) => handleActiveInactive(id)
        }
    ]

    return (
        <>
            <div className="flex justify-between my-4">
                <Form
                    onSubmit={search}
                    hookFormMethods={hookFormMethods}
                >
                    <Button>{!isUnlock ? <Loader2 className="animate-spin" visibility={!isUnlock ? 'visible' : 'hidden'} type="submit" /> : <Search size={20} />} Search</Button>
                </Form>
                <ClientNewFormSheet icon={<Plus size={20} />} triggerLabel={'Add'} onClientCreate={_search} />
            </div>
            <DataTable
                columns={columns}
                data={values ?? []}
                actions={(row) => <TableActions row={row} actions={actions} idKey={'id'} />}
            />
            {editingClient && (
                <ClientNewFormSheet
                    onClientCreate={handleClientEdit}
                    client={editingClient}
                />
            )}
        </>
    )
}