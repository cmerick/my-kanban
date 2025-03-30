import { Header } from "../_components/header";

export default function Layout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    
    return (
        <html lang="en">
            <body
            >
                <Header />
                {children}

            </body>
        </html>
    );
}
