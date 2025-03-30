'use client'
import { DataTable, TableAction } from "@/app/_components/data-table"
import Form from "@/app/_components/form";
import usePreventAsyncFunction from "@/app/_helpers/prevent-async-function.hook";
import ClientResponseDto from "@/app/_models/client/client-response.dto";
import { Button } from "@/components/ui/button";
import { clientFindAll, clientToggleStatus } from "@/services/client/client.service";
import { Loader2, Plus, Search } from "lucide-react"
import { useState } from "react";
import { useForm } from "react-hook-form";
import ClientNewFormSheet from "./client-new-form-sheet";

export default function ClientPageComponent() {
    const [values, setValues] = useState<ClientResponseDto[]>([]);
    const hookFormMethods = useForm<ClientResponseDto>();
    const columnKeys: (keyof typeof values[0] & string)[] = ['id', 'name', 'createdAt', 'updatedAt', 'status',];  // Chaves como string

    const _search = async () => {
        const response = await clientFindAll();

        setValues(response)
    }

    const handleActiveInactive = async (id: string) => {
        const response = clientToggleStatus(id);
        _search();
    }
    const { safeFunction: search, isUnlock } = usePreventAsyncFunction(_search);

    const actions = [
        {
            name: 'Edit',
            method: async (id: string) => handleActiveInactive(id)
        },
        {
            name: 'Change Status',
            method: async (id: string) => handleActiveInactive(id)
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
            <DataTable columnKeys={columnKeys} data={values ?? []} actions={actions as TableAction<any>[]} idKey={'id'} />
        </>
    )
}