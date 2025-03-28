'user client'
import { PropsWithChildren } from 'react';
import { ZodEffects, ZodError, ZodObject, ZodRawShape } from 'zod';
import {
    FieldPath,
    FieldValues,
    FormProvider,
    UseFormReturn,
} from 'react-hook-form';
import AnyObject from '@/helpers/any-object.model';

type CustomSubmit<T> = (data: T) => Promise<void> | void;

type Submit<T> = undefined | CustomSubmit<T>;

interface FormProps<Z extends ZodRawShape, F extends FieldValues> {
    onSubmit?: Submit<F>;
    schema?: ZodObject<Z> | ZodEffects<ZodObject<Z>> | ZodEffects<any>;
    hookFormMethods: UseFormReturn<F>;
}

type Props<Z extends ZodRawShape, F extends FieldValues> = FormProps<Z, F> &
    PropsWithChildren & {
        className?: string;
    };

export default function Form<
    Z extends ZodRawShape = AnyObject,
    F extends FieldValues = AnyObject,
>({ children, onSubmit, schema, hookFormMethods }: Props<Z, F>) {
    const submit = async (formData: F) => {
        try {
            const data = await schema?.parseAsync(formData);
            onSubmit && (await onSubmit((data as F) ?? formData));
        } catch (e) {
            if (e instanceof ZodError) {
                for (const issue of e.errors) {
                    hookFormMethods.setError(
                        issue.path.join('.') as FieldPath<F>,
                        {
                            message: issue.message,
                        },
                        { shouldFocus: true },
                    );
                }
            } else {
                throw e;
            }
        }
    };

    return (
        <FormProvider {...hookFormMethods}>
            <form
                onSubmit={hookFormMethods.handleSubmit(submit)}
            >
                {children}
            </form>
        </FormProvider>
    );
}
