import JobPosting from "@/components/posting";
import { fetchJobPostings } from "@/lib/api";

export default async function Page() {
    const postings = await fetchJobPostings();
    return <>
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-3 p-5">
            {postings.map((posting, index) => (
                <JobPosting key={index} posting={posting} />
            ))}
        </div>
    </>;
}