'use server'
import { BackendResponseErrrorType } from "@/app/_models/errors/bad-response-error";
import { ProjectRequestDto } from "@/app/_models/projects/project-request.dto";
import ProjectResponseDto from "@/app/_models/projects/project-response.dto";
import { kanbanApi } from "@/configuration/api-config";
import { AxiosError } from "axios";
import { NextRequest } from "next/server";

interface Params {
    params: Promise<{
        id: string;
    }>
}

export async function POST(request: NextRequest) {
    const authToken = request.cookies.get('token')?.value;
    if (!authToken) {
        return new Response(JSON.stringify(new Error('Usuário não autorizado')), { status: 401 })
    }
    try {

        const data = await request.json() as ProjectRequestDto;

        const response = await kanbanApi.post(`/projects`, data, {
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

export async function GET(request: NextRequest, { params }: Params) {
    const authToken = request.cookies.get('token')?.value;
    if (!authToken) {
        return new Response(JSON.stringify(new Error('Usuário não autorizado')), { status: 401 })
    }
    try {

        const { id } = await params;

        const response = await kanbanApi.get(`/projects/client/${id}`, {
            headers: {
                "Authorization": `Bearer ${authToken}`
            }
        })

        const clients = response.data as ProjectResponseDto[];

        return new Response(JSON.stringify(clients), { status: 200 })
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