package org.recruitert.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.recruitert.models.JobPosting;
import org.recruitert.models.PostingState;
import org.recruitert.repositories.JobPostingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class JobPostingService {
    private final JobPostingRepository repository;

    public List<JobPosting> findHomePostings() {
        return repository
            .findByStateAndExpiryTimeBefore(PostingState.DEFAULT, LocalDateTime.now());
    }

    @Transactional
    public JobPosting updateJobState(final Long jobId, final PostingState state) {
        final JobPosting jobPosting = repository
            .findById(jobId)
            .orElseThrow();
        jobPosting.setState(state);
        return repository.save(jobPosting);
    }
}
