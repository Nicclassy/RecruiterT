package org.recruitert.repositories;

import org.recruitert.models.JobPosting;
import org.recruitert.models.PostingState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findByStateAndExpiryTimeBefore(PostingState state, LocalDateTime now);
}