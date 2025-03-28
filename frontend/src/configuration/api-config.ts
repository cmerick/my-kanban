import { ApiConstructor } from "@/app/_util/fetch.util";
import { cookies } from "next/headers";

export const API_URL: string = process.env.API_ORL ?? "";

const cookie = await cookies();

const kanbanApi = new ApiConstructor(API_URL, {
    'Authorization': `Bearer ${cookie.get('token')?.value ?? ""}`
})

export { kanbanApi };