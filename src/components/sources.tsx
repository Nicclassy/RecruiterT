import { PostingSource } from "@/models/posting";

export function WorkdayInternal() {
    return <>
        <div className="bg-orange-200 source">
            <p className="text-orange-500">
                {PostingSource.WorkdayInternal}
            </p>
        </div>
    </>;
}

export function WorkdayExternal() {
    return <>
        <div className="bg-blue-200 source">
            <p className="text-blue-500">
                {PostingSource.WorkdayExternal}
            </p>
        </div>
    </>;
}

export function Seek() {
    return <>
        <div className="bg-purple-200 source">
            <p className="text-purple-500">
                {PostingSource.Seek}
            </p>
        </div>
    </>;
}

export function GradConnection() {
    return <>
        <div className="bg-green-200 source">
            <p className="text-green-500">
                {PostingSource.GradConnection}
            </p>
        </div>
    </>;
}