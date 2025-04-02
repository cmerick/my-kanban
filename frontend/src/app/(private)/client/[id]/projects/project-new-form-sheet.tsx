'use client'

import { Button } from "@/components/ui/button";
import {
    Sheet,
    SheetContent,
    SheetHeader,
    SheetTitle,
    SheetTrigger,
} from "@/components/ui/sheet"
import { useForm } from "react-hook-form";
import { Loader2, Rows } from "lucide-react";
import usePreventAsyncFunction from "@/app/_helpers/prevent-async-function.hook";
import Form from "@/app/_components/form";
import FormInputText from "@/app/_components/form-input-text";
import { useEffect, useState } from "react";
import { frontendApi } from "@/configuration/api-config";
import { ProjectRequestDto, ProjectRequestSchema } from "@/app/_models/projects/project-request.dto";
import ProjectResponseDto from "@/app/_models/projects/project-response.dto";
import { UserMeDto } from "@/app/_models/secutiry/user-me.dto";
import { mapper } from "@/app/_util/mapper.util";
import FormInputTextArea from "@/app/_components/form-input-text-area";
interface Props {
    icon?: React.ReactNode;
    triggerLabel?: string;
    onClientCreate: () => Promise<void>,
    project?: ProjectResponseDto | null;
    clientId?: string;
}

export default function ProjectNewFormSheet({ icon, triggerLabel, onClientCreate, project, clientId }: Props) {
    const hookFormMethods = useForm<ProjectRequestDto>({
        defaultValues: project ? {
            title: project.title,
            description: project.description,
            clientId: project.clientId,
            creatorId: project.creatorId,
        } : {
            clientId: clientId
        }
    });
    const [open, setOpen] = useState(false);
    useEffect(() => {
        if (project && !open) {
            hookFormMethods.reset(undefined);
            setOpen(true);
        } else {
            hookFormMethods.reset(undefined);
        }
    }, [project]);

    const _submit = async (data: ProjectRequestDto) => {
        try {
            if (project) {
                await frontendApi.put(`/clients/${project.clientId}/projects/${project.id}`, data);
            } else {
                await frontendApi.post(`/clients/${clientId}/projects`, data);
            }
            await onClientCreate();
            setOpen(false);

        } catch (errors: any) {
            console.error(errors)
        }
    }

    const { safeFunction: submit, isUnlock } = usePreventAsyncFunction(_submit);

    const getUser = async () => {
        const response = await frontendApi('user/me')

        hookFormMethods.setValue('creatorId', response.data.id);
    }

    useEffect(() => {
        getUser();
    }, [])

    return (
        <Sheet open={open} onOpenChange={setOpen}>
            {triggerLabel || icon ? (
                <SheetTrigger className="inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-md text-sm font-medium transition-all disabled:pointer-events-none disabled:opacity-50 [&_svg]:pointer-events-none [&_svg:not([class*='size-'])]:size-4 shrink-0 [&_svg]:shrink-0 outline-none focus-visible:border-ring focus-visible:ring-ring/50 focus-visible:ring-[3px] aria-invalid:ring-destructive/20 dark:aria-invalid:ring-destructive/40 aria-invalid:border-destructive bg-primary text-primary-foreground shadow-xs hover:bg-primary/90 h-9 px-4 py-2 has-[>svg]:px-3">
                    <div>{icon}</div>
                    {triggerLabel}
                </SheetTrigger>
            ) : null}
            <SheetContent>
                <Form
                    onSubmit={submit}
                    hookFormMethods={hookFormMethods}
                    schema={ProjectRequestSchema}
                >
                    <SheetHeader>
                        <SheetTitle>{project ? 'Edit Project' : 'New Project'}</SheetTitle>
                    </SheetHeader>
                    <div className="flex flex-col gap-2 mx-8 ">
                        <FormInputText placeholder="title" autoComplete='title' type="text" name="title" />
                        <FormInputTextArea
                            placeholder="description"
                            autoComplete='description'
                            type="description"
                            name="description"
                            wrap="soft"
                            className="resize-none"
                        />
                        <FormInputText label="Client ID" readOnly className="" placeholder="clientId" autoComplete='clientId' type="clientId" name="clientId" />
                        <FormInputText label="Creator ID" readOnly className="" placeholder="creatorId" autoComplete='creatorId' type="creatorId" name="creatorId" />
                        <Button>{!isUnlock && <Loader2 className="animate-spin" visibility={!isUnlock ? 'visible' : 'hidden'} type="submit" />} {project ? 'Save' : 'Create'}</Button>
                    </div>
                </Form>
            </SheetContent>
        </Sheet>
    )
}