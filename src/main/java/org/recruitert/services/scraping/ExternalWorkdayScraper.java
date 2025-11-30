package org.recruitert.services.scraping;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import lombok.AllArgsConstructor;
import org.recruitert.models.JobPosting;
import org.recruitert.models.PostingSourceScraper;
import org.recruitert.utils.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Qualifier("WORKDAY_EXTERNAL")
@Service
@AllArgsConstructor
public class ExternalWorkdayScraper implements PostingSourceScraper {
    private final static Locator.WaitForOptions WAIT_FOR_OPTIONS = new Locator.WaitForOptions()
        .setState(WaitForSelectorState.ATTACHED)
        .setTimeout(3000);

    private final Browser browser;

    public List<JobPosting> findJobPostings() {
        final Page page = browser.newPage();
        enableCasualJobsFilter(page);

        final ExternalWorkdayPostingFinder postingFinder = new ExternalWorkdayPostingFinder(page);
        final List<String> urls = postingFinder.findPostings();
        return findJobPostings(browser, urls);
    }

    private void enableCasualJobsFilter(final Page page) {
        page.navigate(
            "https://usyd.wd105.myworkdayjobs.com/en-GB/USYD_EXTERNAL_CAREER_SITE"
        );
        page.waitForLoadState(LoadState.NETWORKIDLE);

        final LocatorFactory factory = new LocatorFactory(page);

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
            page,
            WAIT_FOR_OPTIONS,
            new String[] { StringUtils.concatenate(
                "#mainContent > div > div.css-1wnbqgd > div.css-mifb2i >",
                "fieldset > ul > li.css-6n7j50 > div > button"
            )}
        );
        pageLocator.waitUntilPresent();
        page.reload();
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    private List<JobPosting> findJobPostings(final Browser browser, final List<String> urls) {
        final List<JobPosting> postings = new ArrayList<>();
        final Page page = browser.newPage();
        final LocatorFactory factory = new LocatorFactory(page);
        for (final String url : urls) {
            page.reload();
            page.navigate(url);
            page.waitForLoadState(LoadState.NETWORKIDLE);

            final ExternalWorkdayExtractor extractor = new ExternalWorkdayExtractor(factory, url);
            postings.add(JobPosting.from(extractor));
        }

        page.close();
        return postings;
    }

    public static void main(String[] args) {
        final BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false);
        try (
            final Playwright playwright = Playwright.create();
            final Browser browser = playwright.chromium().launch(options)
        ) {
            final ExternalWorkdayScraper scraper = new ExternalWorkdayScraper(browser);
            final List<JobPosting> jobPostings = scraper.findJobPostings();
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