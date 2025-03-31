'use server'

import SignInResponseDto from "@/app/_models/authentication/signin-response.dto";
import { SignInDto } from "@/app/_models/authentication/signin.dto"
import { mapper } from "@/app/_util/mapper.util";
import { KEYCLOAK_CLIENT_ID, KEYCLOAK_GRANT_TYPE, signInUrl } from "@/configuration/keycloak-config"
import { redirect } from "next/navigation";
import { cookies } from "next/headers";
import { frontendApi, kanbanApi } from "@/configuration/api-config";
import { UserMeDto } from "@/app/_models/secutiry/user-me.dto";

export async function signIn(request: SignInDto) {
    const cookie = await cookies();
    const formData = new URLSearchParams();
    formData.append('username', request.username);
    formData.append('password', request.password);
    formData.append('client_id', KEYCLOAK_CLIENT_ID);
    formData.append('grant_type', KEYCLOAK_GRANT_TYPE);

    const response = await fetch(`${signInUrl}/protocol/openid-connect/token`,
        {
            method: 'POST',
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: formData
        },
    );
    const map: SignInResponseDto = await response.json();
    const data = mapper(SignInResponseDto, map)

    if (data.access_token) {
        const expires = new Date();
        expires.setTime(expires.getTime() + (data.expires_in * 1000));
        cookie.set("token", data.access_token, { expires, secure: true });
        redirect('/')

    } else {
        console.error(data)
    }
}

export async function signOut() {
    const cookie = await cookies();
    cookie.delete('token')
    redirect('/');
}

export async function userMe(): Promise<UserMeDto> {
    const response = await frontendApi.get<UserMeDto>(`auth/me`);
    return response.data;
}

