package org.recruitert.services;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.recruitert.models.JobPosting;
import org.recruitert.models.PostingRefreshTime;
import org.recruitert.models.PostingSource;
import org.recruitert.repositories.JobPostingRepository;
import org.recruitert.repositories.PostingRefreshTimeRepository;
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
    private final PostingRefreshTimeRepository refreshTimeRepository;

    @Transactional
    public void updateJobs(final PostingSource source) {
        final Instant now = Instant.now();
        if (!refreshTimeService.canRefresh(source, now))
            return;

        final PostingRefreshTime refreshTime = refreshTimeRepository
            .findById(source)
            .orElseThrow();

        try {
            final List<JobPosting> existing =
                postingRepository.findAllBySource(source);

            final List<JobPosting> scraped =
                postingFinder.findPostings(source);

            final List<JobPosting> updated =
                JobPostingDiff.diff(existing, scraped);

            refreshTime.markSuccess(now);
            postingRepository.saveAll(updated);
        } catch (final OptimisticLockException ex) {
            throw ex;
        } catch (final Exception ex) {
            refreshTime.markFailure(now);
            throw ex;
        }
    }
}
