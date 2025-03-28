'use client'
import React from 'react';
import { PropsWithChildren } from 'react';
import { FieldError, UseFormRegisterReturn } from 'react-hook-form';

export interface FormInputProps extends React.InputHTMLAttributes<HTMLInputElement> {
	className?: string;
	name: string;
	label?: string;
	required?: boolean;
	description?: string;
	error?: FieldError;
}

export interface RegisterFormInputProps {
	registerHookForm?: UseFormRegisterReturn;
}

type Props = FormInputProps & PropsWithChildren;

export default function FormInput({
	children,
	name,
	label,
	description,
	required,
	error,
}: Props) {
	return (
		<div className='field'>
			<label htmlFor={name}>
				{label} {required && ' *'}
			</label>

			{children}

			{description ? (
				<small id={`${name}-help`} className='block'>
					* {description}
				</small>
			) : null}

			{error ? (
				<small id={`${name}-error`} className='p-error block'>
					{error?.message?.toString()}
				</small>
			) : null}
		</div>
	);
}
