package org.recruitert.configs;

import com.microsoft.playwright.Browser;
import org.recruitert.models.PostingSource;
import org.recruitert.models.PostingSourceScraper;
import org.recruitert.services.scraping.ExternalWorkdayScraper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.Map;

@Configuration
public class PostingSourceScraperConfig {
    @Lazy
    @Bean
    public ExternalWorkdayScraper externalWorkdayScraper(final Browser browser) {
        return new ExternalWorkdayScraper(browser);
    }

    @Bean
    public Map<PostingSource, PostingSourceScraper> scrapersBySource(
        final @Qualifier("WORKDAY_EXTERNAL") PostingSourceScraper externalWorkdayScraper
    ) {
        return Map.ofEntries(
            Map.entry(PostingSource.WORKDAY_EXTERNAL, externalWorkdayScraper)
        );
    }
}