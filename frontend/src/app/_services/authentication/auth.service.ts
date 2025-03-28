'use server'

import SignInResponseDto from "@/app/_models/authentication/signin-response.dto";
import { SignInDto } from "@/app/_models/authentication/signin.dto"
import { mapper } from "@/app/_util/mapper.util";
import { KEYCLOAK_CLIENT_ID, KEYCLOAK_GRANT_TYPE, keycloakApi, signInUrl } from "@/configuration/keycloak-config"
import { redirect } from "next/navigation";
import { cookies } from "next/headers";

export async function signIn(request: SignInDto) {
    const cookie = await cookies();
    const formData = new URLSearchParams();
    formData.append('username', request.username);
    formData.append('password', request.password);
    formData.append('client_id', KEYCLOAK_CLIENT_ID);
    formData.append('grant_type', KEYCLOAK_GRANT_TYPE);

    const response = await keycloakApi.post('protocol/openid-connect/token',
        {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        formData
    );
    const data = mapper(SignInResponseDto, response)

    if (data.access_token) {
        const expires = new Date();
        expires.setTime(expires.getTime() + (data.expires_in * 1000));
        cookie.set("token", data.access_token, { expires });
        const session = cookie.get('token')?.value;
        if (session) {
            redirect('/');
        }
    } else {
        console.error(data)
    }
}
