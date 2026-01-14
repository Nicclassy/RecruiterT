package org.recruitert.services;

import lombok.RequiredArgsConstructor;
import org.recruitert.models.JobPosting;
import org.recruitert.models.PostingState;
import org.recruitert.repositories.JobPostingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostingService {
    private final JobPostingRepository repository;

    public List<JobPosting> findHomePostings() {
        return repository.findByStateAndExpiryTimeBefore(
            PostingState.DEFAULT,
            LocalDateTime.now()
        );
    }

    public List<JobPosting> findSavedPostings() {
        return repository.findByState(PostingState.SAVED);
    }

    public List<JobPosting> findArchivedPostings() {
        return repository.findByState(PostingState.ARCHIVED);
    }

    public List<JobPosting> findIgnoredPostings() {
        return repository.findByState(PostingState.IGNORED);
    }

    public JobPosting updateJobState(final Long jobId, final PostingState state) {
        final JobPosting jobPosting = repository
            .findById(jobId)
            .orElseThrow();

        jobPosting.setState(state);
        return repository.save(jobPosting);
    }
}
