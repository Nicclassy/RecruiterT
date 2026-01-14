package org.recruitert.services;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.recruitert.models.JobPosting;
import org.recruitert.models.PostingSource;
import org.recruitert.models.PostingSourceScraper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostingFinderService {
    private final Map<PostingSource, PostingSourceScraper> scrapers;

    public List<JobPosting> findPostings(final PostingSource source) {
        final @NotNull PostingSourceScraper scraper = scrapers.get(source);
        return scraper.findJobPostings();
    }
}
