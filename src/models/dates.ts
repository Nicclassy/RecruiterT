import dayjs from "dayjs";
import duration from "dayjs/plugin/duration";

dayjs.extend(duration);

export type DateDistance = {
    readonly unit: "seconds" | "minutes" | "hours" | "days" | "weeks" | "months" | "years";
    readonly unitAmount: number;
}

export function dateDistance(from: Date, to: Date): DateDistance {
    const diff = dayjs.duration(
        dayjs(to).diff(dayjs(from))
    );
    
    const years = diff.years();
    if (years > 0)
        return {
            unit: "years",
            unitAmount: years
        };

    const months = diff.months();
    if (months > 0)
        return {
            unit: "months",
            unitAmount: months,
        };

    const weeks = diff.weeks();
    if (weeks > 0)
        return {
            unit: "weeks",
            unitAmount: weeks
        };

    const days = diff.days();
    if (days > 0)
        return {
            unit: "days",
            unitAmount: days
        };

    const hours = diff.hours();
    if (hours > 0)
        return {
            unit: "hours",
            unitAmount: hours
        };

    const minutes = diff.minutes();
    if (minutes > 0)
        return {
            unit: "minutes",
            unitAmount: minutes
        };

    return {
        unit: "seconds",
        unitAmount: diff.seconds()
    };
}

export function formatUnit(dist: DateDistance): string {
    return dist.unitAmount == 1 ? dist.unit.slice(0, -1) : dist.unit;
}