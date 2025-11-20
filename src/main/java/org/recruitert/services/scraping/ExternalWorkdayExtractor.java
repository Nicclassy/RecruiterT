package org.recruitert.services.scraping;

import com.microsoft.playwright.*;
import org.jetbrains.annotations.NotNull;
import org.recruitert.models.PostingSource;
import org.recruitert.utils.RelativeDateParser;
import org.recruitert.utils.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public record ExternalWorkdayExtractor(
    @NotNull LocatorFactory factory,
    @NotNull String postingUrl
) implements JobPostingExtractor {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
        "EEEE d MMMM yyyy hh:mm a",
        Locale.ENGLISH
    );

    @Override
    public String url() {
        return postingUrl;
    }

    @Override
    public String title() {
        final Locator titleElement = factory.locator(
            "#mainContent > div > div > div.css-e23il0 > div.css-cabox8 > div > h2"
        );
        return titleElement.textContent();
    }

    @Override
    public LocalDateTime postingDate() {
        final Locator postedElement = factory.locator(
            StringUtils.concatenate(
                "#mainContent > div > div > div.css-e23il0 > div.css-11p01j8 >",
                "div.css-1pv4c4t > div:nth-child(2) > div:nth-child(2) > div > dl > dd"
            )
        );
        return RelativeDateParser.parse(postedElement.textContent());
    }

    @Override
    public LocalDateTime expiryDate() {
        final Locator expiryElement = factory.locator(
            "#mainContent > div > div > div.css-e23il0 > div.css-11p01j8 > div.css-ey7qxc > div"
        );
        final String applicationsCloseText = expiryElement.textContent();
        return LocalDateTime.parse(applicationsCloseText, formatter);
    }

    @Override
    public List<PostingSource> sources() {
        return List.of(PostingSource.WORKDAY_EXTERNAL);
    }
}
