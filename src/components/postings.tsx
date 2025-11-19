import JobPosting from "@/components/posting";
import type { JobPostingInfo } from "@/models/posting";

export function JobPostings({ postings }: { postings: JobPostingInfo[] }) {
    return <>
        <article className="grid grid-cols-1 lg:grid-cols-2 gap-3 bg-white px-7 py-7 rounded-b-3xl">
            {postings.map((posting, index) => (
                <JobPosting key={index} posting={posting} />
            ))}
        </article>
    </>;
}