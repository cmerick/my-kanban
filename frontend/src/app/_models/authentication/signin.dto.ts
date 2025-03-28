import { z } from "zod";

export const SignInSchema = z.object({
    username: z.string(),
    password: z.string()
})

export type SignInDto = z.infer<typeof SignInSchema>;