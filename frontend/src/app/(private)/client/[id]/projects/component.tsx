'use client'
import { DataTable } from "@/app/_components/data-table"
import Form from "@/app/_components/form";
import usePreventAsyncFunction from "@/app/_helpers/prevent-async-function.hook";
import ClientResponseDto from "@/app/_models/client/client-response.dto";
import { Button } from "@/components/ui/button";
import { FolderSymlink, Loader2, Plus, Search } from "lucide-react"
import { useState } from "react";
import { useForm } from "react-hook-form";
import SimpleStatusEnum, { parserSimpleStatus } from "@/app/_models/enum/simple-status.enum";
import { TableActions } from "@/app/_components/data-table-action";
import Link from "next/link";
import { frontendApi } from "@/configuration/api-config";
import { mapperList } from "@/app/_util/mapper.util";
import ProjectResponseDto from "@/app/_models/projects/project-response.dto";
import ProjectNewFormSheet from "./project-new-form-sheet";

interface Props {
    id: string
}

export default function ProjectPageComponent({ id }: Props) {
    const [values, setValues] = useState<ProjectResponseDto[]>([]);
    const [editingClient, setEditingClient] = useState<ProjectResponseDto | null>(null);
    const hookFormMethods = useForm<ProjectResponseDto>();

    const _search = async () => {
        const response = await frontendApi.get(`/clients/${id}/projects`)

        setValues(mapperList(ProjectResponseDto, response.data))
    }

    const handleActiveInactive = async (projectId: string) => {
        await frontendApi.delete(`clients/${id}/projects/${projectId}/toggle-status`);
        await _search();
    }

    const handleEdit = async (entity: ProjectResponseDto) => {
        setEditingClient(entity);
    }

    const handleClientEdit = async () => {
        setEditingClient(null);
        await _search();
    }
    const { safeFunction: search, isUnlock } = usePreventAsyncFunction(_search);

    const columns = (values: ProjectResponseDto[]) => [
        {
            accessorKey: "",
            header: "More",
            cell: (info: any) => <Link href={`client/${info.row.original.id}/projects`} ><FolderSymlink size={20} /></Link>,
        },
        {
            accessorKey: "id",
            header: "#",
            cell: (info: any) => info.getValue(),
        },
        {
            accessorKey: "title",
            header: "Title",
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
            method: async (_id: string, row: ProjectResponseDto) => handleEdit(row)

        },
        {
            name: 'Change Status',
            method: async (id: string, _row: ProjectResponseDto) => handleActiveInactive(id)
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
                <ProjectNewFormSheet icon={<Plus size={20} />} triggerLabel={'Add'} onClientCreate={_search} clientId={id} />
            </div>
            <DataTable
                columns={columns}
                data={values ?? []}
                actions={(row) => <TableActions row={row} actions={actions} idKey={'id'} />}
            />
            {editingClient && (
                <ProjectNewFormSheet
                    onClientCreate={handleClientEdit}
                    project={editingClient}
                />
            )}
        </>
    )
}