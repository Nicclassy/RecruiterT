package org.recruitert.services;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.recruitert.models.JobPosting;
import org.recruitert.models.PostingRefreshTime;
import org.recruitert.models.PostingSource;
import org.recruitert.repositories.JobPostingRepository;
import org.recruitert.utils.JobPostingDiff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostingRefreshService {
    private final PostingFinderService postingFinder;
    private final JobPostingRepository postingRepository;
    private final PostingRefreshTimeService refreshTimeService;

    @Transactional
    public void updateJobs(final PostingSource source) {
        final Instant now = Instant.now();
        final @Nullable PostingRefreshTime refreshTime = refreshTimeService.tryRefresh(source, now);

        if (refreshTime == null)
            return;

        try {
            final List<JobPosting> existing =
                postingRepository.findAllBySource(source);

            final List<JobPosting> scraped =
                postingFinder.findPostings(source);

            final List<JobPosting> updated =
                JobPostingDiff.diff(existing, scraped);

            postingRepository.saveAll(updated);
            refreshTime.markSuccess(now);
        } catch (final Exception ex) {
            refreshTime.markFailure(now);
            throw ex;
        }
    }
}
