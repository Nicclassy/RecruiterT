import React from "react";

type HeadingInfo = {
    title: string
}

const HEADINGS = [
    { title: "Home" },
    { title: "Saved" },
    { title: "Ignored" },
    { title: "Archived" }
];

function Heading(
    { heading, isActive, ...props }: { heading: HeadingInfo, isActive: boolean } & React.HTMLAttributes<HTMLDivElement>
) {
    return <>
        <div className={`item-heading flex-1 ${isActive ? "bg-white" : "bg-[#f7f7f7]"} ${props}`}>
            <h2>{heading.title}</h2>
        </div>
    </>;
}

export default function Headings() {
    return <article className="flex flex-row">
        {HEADINGS.map((heading, index) => (
            <Heading heading={heading} isActive={index == 0} key={index} />
        ))}
    </article>;
}