import { MiddlewareConfig, NextRequest, NextResponse } from "next/server";
import { publicRoutes } from "./configuration/public-routes";
import { jwtDecode } from "jwt-decode";

export function middleware(request: NextRequest) {
    const REDIRECT_WHEN_NOT_AUTHENTICATED_ROUTE = '/sign-in';
    const path = request.nextUrl.pathname;
    const publicRoute = publicRoutes.find(route => route.path === path)
    const authToken = request.cookies.get('token');

    if (!authToken && publicRoute) {
        return NextResponse.next();
    }
    if (!authToken && !publicRoute) {
        const redirectUrl = request.nextUrl.clone();

        redirectUrl.pathname = REDIRECT_WHEN_NOT_AUTHENTICATED_ROUTE;
        console.log(path)
        return NextResponse.redirect(redirectUrl);
    }
    if (authToken && publicRoute && publicRoute.whenAuthenticated === 'redirect') {
        debugger;
        const redirectUrl = request.nextUrl.clone();
        redirectUrl.pathname = '/';
        return NextResponse.redirect(redirectUrl);
    }
    if (authToken && !publicRoute) {
        const decoded = jwtDecode(authToken.value);
        const now = Math.floor(Date.now() / 1000);
        if (now >= Number(decoded.exp)) {
            const redirectUrl = request.nextUrl.clone();
            redirectUrl.pathname = REDIRECT_WHEN_NOT_AUTHENTICATED_ROUTE;
            return NextResponse.redirect(redirectUrl);
        }
        return NextResponse.next();
    }

    return NextResponse.next();
}

export const config: MiddlewareConfig = {
    matcher: [
        /*
    * Match all request paths except for the ones starting with:
    * - api (API routes)
    * - _next/static (static files)
    * - _next/image (image optimization files)
    * - favicon.ico, sitemap.xml, robots.txt (metadata files)
    */
        '/((?!api|_next/static|_next/image|favicon.ico|sitemap.xml|robots.txt).*)',

    ]
}

