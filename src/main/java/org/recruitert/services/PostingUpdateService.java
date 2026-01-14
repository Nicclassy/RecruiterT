package org.recruitert.services;

import lombok.RequiredArgsConstructor;
import org.recruitert.models.PostingSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostingUpdateService {
    private final PostingRefreshService refreshService;

    public void updateAllSources() {
        for (final PostingSource source : PostingSource.values()) {
            refreshService.updateJobs(source);
        }
    }
}