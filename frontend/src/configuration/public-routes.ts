
export const publicRoutes = [
    { path: '/sign-in', whenAuthenticated: 'redirect' },
    { path: '/register', whenAuthenticated: 'redirect' },
    { path: '/pricing', whenAuthenticated: 'next' },
] as const