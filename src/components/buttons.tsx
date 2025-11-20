'use client';

import Link from 'next/link';
import { ArrowRightIcon, BookmarkIcon, EllipsisHorizontalIcon } from "@heroicons/react/24/outline";
import type { JobPostingInfo } from "@/models/posting";
import { useEffect, useRef, useState } from "react";

export function PostingOptionsButton({ posting }: { posting: JobPostingInfo }) {
    const [isOpen, setIsOpen] = useState(false);
    const ref = useRef<HTMLDivElement>(null);

    useEffect(() => {
        function handleClick(event: MouseEvent) {
            if (ref.current && !ref.current.contains(event.target as Node))
                setIsOpen(false);
        }

        if (isOpen)
            document.addEventListener("mousedown", handleClick);
        return () => document.removeEventListener("mousedown", handleClick);
    }, [isOpen]);

    return <div className="relative inline-block" ref={ref}>
        <button className="w-10 h-10" onClick={() => setIsOpen(!isOpen)}>
            <EllipsisHorizontalIcon className="hover:stroke-(--primary)"/>
        </button>

        {isOpen && (
        <div
            className="posting-options absolute right-0 -translate-y-2.5 flex flex-col z-10"
        >
            <button
                className="primary-colour-hover w-full text-left py-1 px-3 rounded-t-lg"
                onClick={() => {
                    setIsOpen(false)
                }}
            >
                Archive
            </button>
            <button
                className="primary-colour-hover w-full text-left py-1 px-3 rounded-b-lg"
                onClick={() => {
                    setIsOpen(false)
                }}
            >
                Ignore
            </button>
        </div>
        )}
    </div>;
}

export function ExternalJobLink({ posting }: { posting: JobPostingInfo }) {
    return <>
        <Link href={posting.url} className="border-blue-500">
            <ArrowRightIcon className="w-10 h-10 p-1 text-white bg-(--primary) rounded-md" />
        </Link>
    </>;
}

export function SavePostingButton({ posting }: { posting: JobPostingInfo }) {
    return <>
        <button onClick={() => { void posting; }}>
            <BookmarkIcon className="w-10 h-10 hover:stroke-(--primary)" />
        </button>
    </>;
}

export function SettingsButton() {
    // Customisation of the equivalent JSX of
    // @heroicons/react/24/outline/Cog6ToothIcon
    return <>
        <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="white"
            viewBox="0 0 24 24"
            strokeWidth={0.0}
            stroke="gray"
            className="w-12 h-12"
        >
            <path
                strokeLinecap="round"
                strokeLinejoin="round"
                fill="gray"
                d="M9.594 3.94c.09-.542.56-.94 1.11-.94h2.593c.55 0 1.02.398 1.11.94l.213 1.281c.063.374.313.686.645.87.074.04.147.083.22.127.325.196.72.257 1.075.124l1.217-.456a1.125 1.125 0 0 1 1.37.49l1.296 2.247a1.125 1.125 0 0 1-.26 1.431l-1.003.827c-.293.241-.438.613-.43.992a7.723 7.723 0 0 1 0 .255c-.008.378.137.75.43.991l1.004.827c.424.35.534.955.26 1.43l-1.298 2.247a1.125 1.125 0 0 1-1.369.491l-1.217-.456c-.355-.133-.75-.072-1.076.124a6.47 6.47 0 0 1-.22.128c-.331.183-.581.495-.644.869l-.213 1.281c-.09.543-.56.94-1.11.94h-2.594c-.55 0-1.019-.398-1.11-.94l-.213-1.281c-.062-.374-.312-.686-.644-.87a6.52 6.52 0 0 1-.22-.127c-.325-.196-.72-.257-1.076-.124l-1.217.456a1.125 1.125 0 0 1-1.369-.49l-1.297-2.247a1.125 1.125 0 0 1 .26-1.431l1.004-.827c.292-.24.437-.613.43-.991a6.932 6.932 0 0 1 0-.255c.007-.38-.138-.751-.43-.992l-1.004-.827a1.125 1.125 0 0 1-.26-1.43l1.297-2.247a1.125 1.125 0 0 1 1.37-.491l1.216.456c.356.133.751.072 1.076-.124.072-.044.146-.086.22-.128.332-.183.582-.495.644-.869l.214-1.28Z"
            />
            <path
                strokeLinecap="round"
                strokeLinejoin="round"
                fill="#f7f7f7"
                d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"
            />
        </svg>
    </>;
}