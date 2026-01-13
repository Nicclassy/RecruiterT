package org.recruitert.services.scraping;

import com.microsoft.playwright.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.recruitert.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public record ExternalWorkdayPostingFinder(@NotNull Page homepage) {
    private final static String NEXT_PAGE_BUTTON_SELECTOR =
        "#mainContent > div > div.css-1142bqn > section > div.css-3z7fsk > nav > div > button";
    private final static String EXTERNAL_WORKDAY_BASE_URL = "https://usyd.wd105.myworkdayjobs.com";

    public List<String> findPostings() {
        final List<String> postings = new ArrayList<>();
        final LocatorFactory factory = new LocatorFactory(homepage);

        int pageNumber = 1;
        boolean pagesRemaining;
        do {
            final List<String> links = findPostingLinks(homepage);
            final @Nullable Locator locator = factory.locatorIfPresent(
                NEXT_PAGE_BUTTON_SELECTOR + (pageNumber == 0 ? "" : ":nth-child(3)")
            );
            postings.addAll(links);

            pagesRemaining = locator != null;
            pageNumber++;
            if (locator != null)
                locator.click();
        } while (pagesRemaining);

        homepage.close();
        return postings;
    }

    private static List<String> findPostingLinks(final @NotNull Page page) {
        final LocatorFactory factory = new LocatorFactory(page);
        final Locator parent = factory.locator(
            "#mainContent > div > div.css-1142bqn > section > ul"
        );
        final List<Locator> children = parent.locator("> *").all();
        final List<String> links = new ArrayList<>();
        for (final Locator child : children) {
            final @Nullable Locator link = factory.tryLocate(child, "a");
            if (link != null) {
                try {
                    final String postingUrl = link.getAttribute("href");
                    links.add(
                        StringUtils.relativeUrlToAbsolute(EXTERNAL_WORKDAY_BASE_URL, postingUrl)
                    );
                } catch (final PlaywrightException ignored) {}
            }
        }

        return links;
    }
}
