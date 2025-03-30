import { House } from "lucide-react";
import ClientPageComponent from "./component";

export default async function Client() {
    return (
        <div className="">
            <div className="flex items-center gap-1">
                <House size={12} /><small>/ Client</small>
            </div>
            <ClientPageComponent />
        </div>
    );
}
