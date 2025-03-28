import { z } from "zod";

export const ClientRequestSchema = z.object({
    name: z.string(),
})

export type ClientRequestDto = z.infer<typeof ClientRequestSchema>;