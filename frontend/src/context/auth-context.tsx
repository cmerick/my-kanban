'use client'

import { SignInDto } from "@/app/_models/authentication/signin.dto";
import { signIn, signOut } from "@/services/authentication/auth.service";
import { getCookie } from "cookies-next";
import React, { createContext, useContext } from "react";

type AuthContextType = {
    isAuthenticated: boolean;
    login: (entity: SignInDto) => Promise<void>;
    logout: () => void;
    recoveryToken: () => string | undefined;
}

export const AuthContext = createContext({} as AuthContextType);

export const useAuth = () => useContext(AuthContext)

export function AuthContextProvider({ children }: { children: React.ReactNode }) {
    var isAuthenticated = !!recoveryToken();


    async function login(entity: SignInDto) {
        await signIn(entity);
    }

    async function logout() {
        await signOut()
    }

    function recoveryToken() {
        const cookie = getCookie("token");
        const token = cookie?.toString();
        return token;
    }

    return (
        <AuthContext.Provider value={{ isAuthenticated, login, logout, recoveryToken }}>
            {children}
        </AuthContext.Provider>
    )
}