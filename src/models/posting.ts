export enum PostingSource {
    WorkdayInternal = "Workday (Internal)",
    WorkdayExternal = "Workday (External)",
    Seek = "Seek",
    GradConnection = "GradConnection"
}

export type JobPostingInfo = {
    readonly id: number;
    readonly title: string;
    readonly url: string;
    readonly postingDate: Date;
    readonly expiryDate: Date;
    readonly sources: PostingSource[];
};