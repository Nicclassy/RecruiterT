package org.recruitert.configs;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import org.recruitert.services.scraping.ExternalWorkdayScraper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class PostingSourceScraperConfig {
    @Bean(destroyMethod = "close")
    public Playwright playwright() {
        return Playwright.create();
    }

    @Lazy
    @Bean(destroyMethod = "close")
    public Browser browser(final Playwright playwright) {
        return playwright.chromium().launch();
    }

    @Lazy
    @Bean
    public ExternalWorkdayScraper externalWorkdayScraper(final Browser browser) {
        return new ExternalWorkdayScraper(browser);
    }
}