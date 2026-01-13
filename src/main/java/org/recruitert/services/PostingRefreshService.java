package org.recruitert.services;

import lombok.RequiredArgsConstructor;
import org.recruitert.models.JobPosting;
import org.recruitert.models.PostingSource;
import org.recruitert.repositories.JobPostingRepository;
import org.recruitert.utils.JobPostingDiff;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostingRefreshService {
    private final PostingFinderService postingFinder;
    private final JobPostingRepository postingRepository;
    private final PostingRefreshTimeService refreshTimeService;

    public void updateJobs(final PostingSource source) {
        final Instant now = Instant.now();
        if (!refreshTimeService.canRefresh(source, now))
            return;

        try {
            final List<JobPosting> existing =
                postingRepository.findAllBySource(source);

            final List<JobPosting> scraped =
                postingFinder.findPostings(source);

            final List<JobPosting> updated =
                JobPostingDiff.diff(existing, scraped);

            postingRepository.saveAll(updated);
            refreshTimeService.markSuccess(source, now);
        } catch (Exception ex) {
            refreshTimeService.markFailure(source, now);
            throw ex;
        }
    }
}
