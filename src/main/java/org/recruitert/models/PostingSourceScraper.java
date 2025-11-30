package org.recruitert.models;

import java.util.List;

public interface PostingSourceScraper {
    List<JobPosting> findJobPostings();
}
