'use server'
import ClientResponseDto from "@/app/_models/client/client-response.dto";
import { BackendResponseErrrorType } from "@/app/_models/errors/bad-response-error";
import { UserMeDto } from "@/app/_models/secutiry/user-me.dto";
import { kanbanApi } from "@/configuration/api-config";
import { AxiosError } from "axios";
import { NextRequest } from "next/server";


export async function GET(request: NextRequest) {
    const authToken = request.cookies.get('token')?.value;

    if (!authToken) {
        return new Response(JSON.stringify(new Error('Usuário não autorizado')), { status: 401 })
    }

    try {
        const result = await kanbanApi.get('/auth/me', {
            headers: {
                "Authorization": `Bearer ${authToken}`
            }
        })

        const clients = result.data as UserMeDto;

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