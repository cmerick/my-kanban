import { ApiConstructor } from "@/app/_util/fetch.util";

export const KEYCLOAK_URL: string = process.env.KEYCLOAK_ORL ?? "";
export const KEYCLOAK_REALM: string = process.env.KEYCLOAK_REALM ?? "";
export const KEYCLOAK_CLIENT_ID: string = process.env.KEYCLOAK_CLIENT_ID ?? "";
export const KEYCLOAK_GRANT_TYPE: string = process.env.KEYCLOAK_GRANT_TYPE ?? "";

export const signInUrl: string = `${KEYCLOAK_URL}/realms/${KEYCLOAK_REALM}/`

const keycloakApi = new ApiConstructor(signInUrl,)

export { keycloakApi };