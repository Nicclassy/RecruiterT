package org.recruitert.utils;

import lombok.experimental.UtilityClass;
import org.recruitert.models.JobPosting;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public final class JobPostingDiff {
    public static List<JobPosting> diff(final List<JobPosting> storedJobs, final List<JobPosting> foundJobs) {
        final Map<String, JobPosting> storedJobsByUrl = storedJobs
            .stream()
            .collect(Collectors.toMap(JobPosting::getUrl, posting -> posting));

        final Map<String, JobPosting> result = new LinkedHashMap<>();
        // Replace job postings with more recent occurrences
        for (final JobPosting foundJob : foundJobs) {
            final JobPosting storedJob = storedJobsByUrl.get(foundJob.getUrl());
            if (storedJob != null) {
                foundJob.setState(storedJob.getState());
                foundJob.setId(storedJob.getId());
            }
            result.put(foundJob.getUrl(), foundJob);
        }

        // Include stored jobs that were not found
        for (final JobPosting storedJob : storedJobs) {
            if (!result.containsKey(storedJob.getUrl())) {
                // If the job wasn't found BUT shouldn't be expired, store the expiration time
                if (storedJob.hasExpiredPrematurely())
                    storedJob.setExpiryTime(LocalDateTime.now());
                result.put(storedJob.getUrl(), storedJob);
            }
        }

        return new ArrayList<>(result.values());
    }
}
