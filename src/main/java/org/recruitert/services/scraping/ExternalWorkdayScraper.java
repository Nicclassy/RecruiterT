package org.recruitert.services.scraping;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.jetbrains.annotations.NotNull;
import org.recruitert.models.JobPosting;
import org.recruitert.utils.StringUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public record ExternalWorkdayScraper(@NotNull Browser browser) {
    private final static Locator.WaitForOptions WAIT_FOR_VISIBLE_OPTIONS = new Locator.WaitForOptions()
        .setState(WaitForSelectorState.VISIBLE)
        .setTimeout(3000);

    public Page searchCasualJobs() {
        final Page currentPage = browser.newPage();
        currentPage.navigate(
            "https://usyd.wd105.myworkdayjobs.com/en-GB/USYD_EXTERNAL_CAREER_SITE"
        );
        currentPage.waitForLoadState(LoadState.NETWORKIDLE);

        final LocatorFactory factory = new LocatorFactory(currentPage);

        final Locator moreButton = factory.locator(
            "#mainContent > div > div.css-1wnbqgd > fieldset > div:nth-child(5) > button"
        );
        moreButton.click();

        final Locator casualJobTypeButton = factory.locator(
            "#\\39 16f7f96511f01d5d826eba1ad0f499b"
        );
        casualJobTypeButton.click();

        final Locator viewJobsButton = factory.locator(
            "body > div:nth-child(2) > div > div.css-19dv0c6 > div > div > div > button"
        );
        viewJobsButton.click();

        final PageLocator pageLocator = new PageLocator(
            currentPage,
            WAIT_FOR_VISIBLE_OPTIONS,
            new String[] { StringUtils.concatenate(
                "#mainContent > div > div.css-1wnbqgd > div.css-mifb2i >",
                "fieldset > ul > li.css-6n7j50 > div > button"
            )}
        );
        pageLocator.waitUntilPresent();
        return currentPage;
    }

    public List<JobPosting> findJobs(final @NotNull List<String> urls) {
        try (
            final ExecutorService executor = Executors.newCachedThreadPool()
        ) {
            final List<CompletableFuture<JobPosting>> futures = urls
                .stream()
                .map(url -> CompletableFuture.supplyAsync(() -> {
                    final Page page = browser.newPage();
                    page.navigate(url);
                    page.waitForLoadState(LoadState.NETWORKIDLE);

                    final LocatorFactory factory = new LocatorFactory(page);
                    final ExternalWorkdayExtractor extractor = new ExternalWorkdayExtractor(factory, url);
                    return JobPosting.from(extractor);
                }, executor))
                .toList();

            final CompletableFuture<Void> all = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
            );
            all.join();

            return futures.stream()
                .map(CompletableFuture::join)
                .toList();
        }
    }

    public static void main(String[] args) {
        final BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false);
        try (
            final Playwright playwright = Playwright.create();
            final Browser browser = playwright.chromium().launch(options)
        ) {
            final ExternalWorkdayScraper scraper = new ExternalWorkdayScraper(browser);
            final Page page = scraper.searchCasualJobs();

            final ExternalWorkdayPostingFinder postingFinder = new ExternalWorkdayPostingFinder(page);
            final List<String> urls = postingFinder.findPostings();

            for (final String url : urls) {
                System.out.println(url);
            }

            final List<JobPosting> jobPostings = scraper.findJobs(urls);
            for (final JobPosting jobPosting : jobPostings) {
                System.out.println(jobPosting);
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}