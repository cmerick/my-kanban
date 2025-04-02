import { z } from "zod";

export const ProjectRequestSchema = z.object({
    creatorId: z.string().uuid(),
    clientId: z.string().uuid(),
    title: z.string(),
    description: z.string().optional().nullable(),
})

export type ProjectRequestDto = z.infer<typeof ProjectRequestSchema>;