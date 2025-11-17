import "@/styles/globals.css";

import { type Metadata } from "next";
import { Noto_Sans } from "next/font/google";
import React from "react";

export const metadata: Metadata = {
  title: "RecruiterT"
};

const noto_sans = Noto_Sans({
  subsets: ["latin"],
  variable: "--font-noto-sans",
});

export default function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  return (
    <html lang="en" className={`${noto_sans.variable}`}>
      <body>{children}</body>
    </html>
  );
}
