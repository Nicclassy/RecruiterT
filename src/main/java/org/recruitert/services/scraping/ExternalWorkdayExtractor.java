package org.recruitert.services.scraping;

import com.microsoft.playwright.*;
import org.jetbrains.annotations.NotNull;
import org.recruitert.models.JobPostingExtractor;
import org.recruitert.models.PostingKind;
import org.recruitert.models.PostingOrExpiryDate;
import org.recruitert.models.PostingSource;
import org.recruitert.utils.StringUtils;
import org.recruitert.utils.TemporalValueParser;
import org.recruitert.utils.TextExtractor;

public record ExternalWorkdayExtractor(
    @NotNull LocatorFactory factory,
    @NotNull String postingUrl,
    @NotNull PostingKind postingKind
) implements JobPostingExtractor {
    @Override
    public String url() {
        return postingUrl;
    }

    @Override
    public PostingKind kind() {
        return postingKind;
    }

    @Override
    public String title() {
        final Locator titleElement = factory.locator(
            "xpath=//*[@id='mainContent']/div/div[1]/div[1]/div[1]/div/h2"
        );
        return titleElement.textContent();
    }

    @Override
    public PostingOrExpiryDate postingDate() {
        final Locator postedElement = factory.locator(
            StringUtils.concatenate(
                "#mainContent > div > div > div.css-e23il0 > div.css-11p01j8 >",
                "div.css-1pv4c4t > div:nth-child(2) > div:nth-child(2) > div > dl > dd"
            )
        );
        return new PostingOrExpiryDate(TemporalValueParser.parse(postedElement.textContent()));
    }

    @Override
    public PostingOrExpiryDate expiryDate() {
        final Locator expiryElement = factory.locator(
            "#mainContent > div > div.css-gk87zv > div.css-e23il0 > div.css-11p01j8 > div.css-ey7qxc > div",
            "//*[@id=\"mainContent\"]/div/div[1]/div[1]/div[3]/div[2]/div"
        );
        final String applicationsCloseText = expiryElement.textContent();
        final String applicationsCloseDate = TextExtractor.extractApplicationsCloseText(applicationsCloseText);
        return new PostingOrExpiryDate(TemporalValueParser.parse(applicationsCloseDate));
    }

    @Override
    public PostingSource source() {
        return PostingSource.WORKDAY_EXTERNAL;
    }
}
