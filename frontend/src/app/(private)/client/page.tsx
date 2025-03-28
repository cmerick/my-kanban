import { DataTable } from "@/app/_components/data-table";
import ClientResponseDto from "@/app/_models/client/client-response.dto";
import { clientFindAll } from "@/app/_services/client/client.service";
import { createColumns } from "@/app/_util/create-columns";
import { House } from "lucide-react";

export default async function Client() {
    const response = await clientFindAll();
    const columnKeys: (keyof typeof response[0] & string)[] = ['id', 'name', 'createdAt', 'updatedAt', 'status',];  // Chaves como string

    return (
        <div className="">
            <div className="flex items-center gap-1">
                <House size={12} /><small>/ Client</small>
            </div>
            <DataTable columnKeys={columnKeys} data={response} />
        </div>
    );
}
