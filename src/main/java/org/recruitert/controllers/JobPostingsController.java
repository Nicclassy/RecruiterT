package org.recruitert.controllers;

import lombok.RequiredArgsConstructor;
import org.recruitert.models.*;
import org.recruitert.services.JobPostingService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/jobs")
@RequiredArgsConstructor
public final class JobPostingsController {
    private final JobPostingService service;

    @GetMapping("/home")
    public List<JobPosting> homeJobs() {
        return service.findHomePostings();
    }

    @GetMapping("/saved")
    public List<JobPosting> savedJobs() {
        return service.findSavedPostings();
    }

    @GetMapping("/archived")
    public List<JobPosting> archivedJobs() {
        return service.findArchivedPostings();
    }

    @GetMapping("/ignored")
    public List<JobPosting> ignoredJobs() {
        return service.findIgnoredPostings();
    }

    @PatchMapping("/update/{jobId}")
    public JobPosting updateJob(final @PathVariable Long jobId, final @RequestParam PostingState state) {
        return service.updateJobState(jobId, state);
    }

    @GetMapping("/fake")
    public List<JobPosting> fakeJobs() {
        final List<JobPosting> jobPostings = new ArrayList<>();
        final JobPosting jobPosting = new JobPosting();
        jobPosting.setTitle("The backend sent you some data");
        jobPosting.setUrl("https://google.com");
        jobPosting.setPostingDate(new PostingOrExpiryDate(new TemporalValue.Date(
            LocalDate.now().minusDays(1)
        )));
        jobPosting.setExpiryDate(new PostingOrExpiryDate(new TemporalValue.Time(
            LocalDateTime.now().plusHours(6))
        ));
        jobPosting.setSource(PostingSource.WORKDAY_INTERNAL);
        jobPostings.add(jobPosting);
        return jobPostings;
    }
}
