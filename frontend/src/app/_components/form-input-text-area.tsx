'use client'

import { Controller, useFormContext } from 'react-hook-form';
import FormInput, { FormInputProps } from './form-Input';
import { Textarea } from '@/components/ui/textarea';

type Props = FormInputProps & React.ComponentProps<'textarea'>;

export default function FormInputTextArea({
    className,
    name,
    label,
    description,
    required,
    ...rest
}: Props) {
    const { control } = useFormContext();

    return (
        <Controller
            name={name}
            control={control}
            render={({ field, fieldState: { error } }) => {
                return (
                    <FormInput
                        className={className}
                        name={name}
                        label={label}
                        description={description}
                        required={required}
                        error={error}
                    >
                        <Textarea
                            id={name}
                            {...field}
                            className={className}
                        />
                    </FormInput>
                );
            }}
        />
    );
}
