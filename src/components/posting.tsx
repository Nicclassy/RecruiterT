import { type JobPostingInfo, PostingSource } from "@/models/posting";
import { SavePostingButton, ExternalJobLink, PostingOptionsButton } from "@/components/buttons";
import { type DateDistance, dateDistance, formatUnit } from "@/models/dates";
import * as Sources from "@/components/sources";

function JobExpiry({ date, posting }: { date: Date, posting: JobPostingInfo }) {
    function displayRedText(expiry: DateDistance) {
        return expiry.unit === "hours" || expiry.unit === "minutes" || expiry.unit === "seconds";
    }

    const postingHasExpired = date >= posting.expiryDate;
    const expiry = postingHasExpired
        ? dateDistance(posting.expiryDate, date)
        : dateDistance(date, posting.expiryDate);

    if (postingHasExpired)
        return <span className="opacity-70">Expired {expiry.unitAmount} {formatUnit(expiry)} ago</span>;

    if (displayRedText(expiry))
        return <span className="opacity-70 text-red-500">Expires in {expiry.unitAmount} {formatUnit(expiry)}</span>;
    return <span className="opacity-70">Expires in {expiry.unitAmount} {formatUnit(expiry)}</span>;
}

function JobPosted({ date, posting }: { date: Date, posting: JobPostingInfo }) {
    const posted = dateDistance(posting.postingDate, date);
    return <p>Posted {posted.unitAmount} {formatUnit(posted)} ago</p>;
}

export default function JobPosting({ posting }: { posting: JobPostingInfo }) {
    const now = new Date();
    return <>
        <div className="flex flex-col px-6 py-3 gap-1 border border-gray-300 rounded-[35px]">
            <div className="flex flex-col gap-2">
                <div>
                    <div className="flex flex-row">
                        <h2 className="font-semibold text-lg">{posting.title}</h2>
                        <div className="flex-grow" />
                        <PostingOptionsButton posting={posting} />
                        <div />
                    </div>
                    <JobPosted date={now} posting={posting} />
                </div>
                <div className="flex flex-row gap-2">
                    {posting.sources.map((source, index) => (
                        <span key={index}>
                        {(() => {
                            switch (source) {
                                case PostingSource.WorkdayInternal:
                                    return <Sources.WorkdayInternal />;
                                case PostingSource.WorkdayExternal:
                                    return <Sources.WorkdayExternal />;
                                case PostingSource.Seek:
                                    return <Sources.Seek />;
                                case PostingSource.GradConnection:
                                    return <Sources.GradConnection />;
                            }
                        })()}
                    </span>
                    ))}
                </div>
            </div>
            <div className="flex-grow" />
            <div className="flex flex-row items-center gap-2">
                <JobExpiry date={now} posting={posting}/>
                <div className="flex-grow" />
                <SavePostingButton posting={posting} />
                <ExternalJobLink posting={posting} />
                <div />
            </div>
        </div>
    </>;
}