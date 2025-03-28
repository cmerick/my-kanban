'use client'

import { Controller, useFormContext } from 'react-hook-form';
import FormInput, { FormInputProps } from './form-Input';
import { Input } from '@/components/ui/input';

type Props = FormInputProps;

export default function FormInputText({
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
                        <Input
                            id={name}
                            {...field}
                            className={className}
                            {...rest}
                        />
                    </FormInput>
                );
            }}
        />
    );
}
