import { PostingSource } from "@/models/posting";

export function WorkdayInternal() {
    return <>
        <div className="bg-orange-300 source">
            <p className="text-orange-500">
                {PostingSource.WorkdayInternal}
            </p>
        </div>
    </>;
}

export function WorkdayExternal() {
    return <>
        <div className="bg-blue-300 source">
            <p className="text-blue-500">
                {PostingSource.WorkdayExternal}
            </p>
        </div>
    </>;
}

export function Seek() {
    return <>
        <div className="bg-purple-300 source">
            <p className="bg-purple-500">
                {PostingSource.GradConnection}
            </p>
        </div>
    </>;
}

export function GradConnection() {
    return <>
        <div className="bg-green-300 rounded-full">
            <p className="bg-green-500">
                {PostingSource.GradConnection}
            </p>
        </div>
    </>;
}