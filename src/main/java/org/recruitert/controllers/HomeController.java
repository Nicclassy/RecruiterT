package org.recruitert.controllers;

import org.recruitert.models.JobPosting;
import org.recruitert.models.PostingSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
public final class HomeController {
    @GetMapping
    public List<JobPosting> homeJobs() {
        final List<JobPosting> jobPostings = new ArrayList<>();
        final JobPosting jobPosting = new JobPosting();
        jobPosting.setTitle("The backend sent you some data");
        jobPosting.setUrl("https://google.com");
        jobPosting.setPostingDate(LocalDateTime.now().minusDays(1));
        jobPosting.setPostingDate(LocalDateTime.now().plusHours(6));
        jobPosting.setSources(
            List.of(
                PostingSource.WORKDAY_INTERNAL,
                PostingSource.WORKDAY_EXTERNAL
            )
        );
        jobPostings.add(jobPosting);
        return jobPostings;
    }
}
