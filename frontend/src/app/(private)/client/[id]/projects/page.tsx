import { House } from "lucide-react";
import ProjectPageComponent from "./component";

interface Params {
    params: Promise<{
        id: string;
    }>
}

export default async function Projects({ params }: Params) {
    const { id } = await params;
    return (
        <div className="">
            <div className="flex items-center gap-1">
                <House size={12} /><small>/ Client / Projects </small>
            </div>
            <ProjectPageComponent id={id} />
        </div>
    );
}
