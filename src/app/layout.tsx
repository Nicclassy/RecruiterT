import "@/styles/globals.css";

import { type Metadata } from "next";
import { Noto_Sans } from "next/font/google";
import React from "react";
import { SettingsButton } from "@/components/buttons";
import Headings from "@/components/headings";

export const metadata: Metadata = {
    title: "RecruiterT"
};

const noto_sans = Noto_Sans({
    subsets: ["latin"],
    variable: "--font-noto-sans",
});

function RecruiterT() {
    return <>
        <h1 className="text-4xl font-bold">
            <span className="text-black mr-1">Recruiter</span>
            <span className="text-white bg-(--primary) px-1 py-2 rounded-xl">{"<T>"}</span>
        </h1>
    </>;
}

export default function RootLayout(
    { children }: Readonly<{ children: React.ReactNode }>)
{
    return (
        <html lang="en">
        <body className={`${noto_sans.variable} px-15 pb-15 bg-[#edeef2]`}>
            <header className="mb-10 mt-5 flex flex-row">
                <RecruiterT />
                <div className="flex-grow" />
                <SettingsButton />
            </header>
            <Headings />
            {children}
        </body>
        </html>
    );
}
