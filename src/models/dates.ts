import dayjs from "dayjs";
import duration from "dayjs/plugin/duration";

dayjs.extend(duration);

const UNIT_ORDER = [
    "years",
    "months",
    "weeks",
    "days",
    "hours",
    "minutes",
    "seconds",
] as const;

type TimeUnit = typeof UNIT_ORDER[number];

export type DateDistance = {
    readonly milliseconds: number;
    readonly larger: {
        readonly unit: TimeUnit;
        readonly unitAmount: number;
    };
    readonly smaller?: {
        readonly unit: TimeUnit;
        readonly unitAmount: number;
    };
}

export function dateDistance(from: Date, to: Date): DateDistance {
    const diff = dayjs.duration(
        dayjs(to).diff(dayjs(from))
    );

    const values: Record<TimeUnit, number> = {
        years: diff.years(),
        months: diff.months(),
        weeks: diff.weeks(),
        days: diff.days(),
        hours: diff.hours(),
        minutes: diff.minutes(),
        seconds: diff.seconds(),
    };

    const index = UNIT_ORDER.findIndex(unit => values[unit] > 0);
    if (index === -1)
        return {
            milliseconds: 0,
            larger: {
                unit: "seconds",
                unitAmount: values["seconds"],
            }
        };

    const milliseconds = to.getTime() - from.getTime();
    const largerUnit = UNIT_ORDER[index]!;
    const smallerUnit = UNIT_ORDER[index + 1]!;
    if (values[smallerUnit] == 0)
        return {
            milliseconds,
            larger: {
                unit: largerUnit,
                unitAmount: values[largerUnit]
            }
        }

    return {
        milliseconds,
        larger: {
            unit: largerUnit,
            unitAmount: values[largerUnit]
        },
        smaller: {
            unit: smallerUnit,
            unitAmount: values[smallerUnit]
        }
    };
}

export function formatTimeInfo(dist: DateDistance): string {
    function formatUnit(unit: TimeUnit, amount: number): string {
        return amount === 1 ? unit.slice(0, -1) : unit;
    }

    let info = `${dist.larger.unitAmount} ${formatUnit(dist.larger.unit, dist.larger.unitAmount)}`;
    if (dist.smaller)
        info += ` and ${dist.smaller.unitAmount} ${formatUnit(dist.smaller.unit, dist.smaller.unitAmount)}`;
    return info;
}
