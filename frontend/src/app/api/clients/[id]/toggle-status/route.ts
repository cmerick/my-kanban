'use server'
import { BackendResponseErrrorType } from "@/app/_models/errors/bad-response-error";
import { kanbanApi } from "@/configuration/api-config";
import { AxiosError } from "axios";
import { NextRequest } from "next/server";

interface Params {
    params: Promise<{
        id: string;
    }>
}

export async function DELETE(request: NextRequest, { params }: Params) {
    const authToken = request.cookies.get('token')?.value;
    if (!authToken) {
        return new Response(JSON.stringify(new Error('Usuário não autorizado')), { status: 401 })
    }
    try {
        const { id } = await params;

        const response = await kanbanApi.delete(`/clients/${id}/toggle-status`, {
            headers: {
                "Authorization": `Bearer ${authToken}`
            }
        })


        return new Response(null, { status: 200 })
    } catch (e) {
        const axiosError = e as AxiosError;
        const { status, error } = axiosError.response?.data as BackendResponseErrrorType;

        if (status) {
            return new Response(JSON.stringify(new AxiosError(error, status.toString())), { status: status })
        } else {
            return new Response(JSON.stringify(new AxiosError(axiosError.message, axiosError.code)),
                { status: axiosError.status || 500 });
        }
    }

}