import { getHomeJobPostings } from "@/lib/api";
import { JobPostings } from "@/components/postings";

export default async function Page() {
    const postings = await getHomeJobPostings();
    return <JobPostings postings={postings} />;
}