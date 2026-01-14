package org.recruitert.repositories;

import org.recruitert.models.JobPosting;
import org.recruitert.models.PostingSource;
import org.recruitert.models.PostingState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findByStateAndExpiryTimeBefore(PostingState state, LocalDateTime now);

    @Query("""
    select jp from JobPosting jp
    join jp.sources s
    where s = :source
    """)
    List<JobPosting> findAllBySource(@Param("source") PostingSource source);

    List<JobPosting> findByState(PostingState state);
}