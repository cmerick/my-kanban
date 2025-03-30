'use server'

import { ClientRequestDto } from "@/app/_models/client/client-request.dto";
import ClientResponseDto from "@/app/_models/client/client-response.dto";
import { kanbanApi } from "@/configuration/api-config";


export async function clientCreate(request: ClientRequestDto): Promise<ClientResponseDto> {
    const response = await kanbanApi.post('/clients', request);
    return response.data;
}

export async function clientUpdate(id: string, request: ClientRequestDto) {
    const response = await kanbanApi.put(`/clients/${id}`, request);
    return response;
}

export async function clientFindAll(): Promise<ClientResponseDto[]> {
    const response = await kanbanApi.get('/clients',);
    return response.data;
}

export async function clientFindById(id: string) {
    const response = await kanbanApi.get(`/clients/${id}`);
    return response.data;
}

export async function clientToggleStatus(id: string) {
    const response = await kanbanApi.put(`/clients/${id}/toggle-status`);
    return response.data;
}
