package org.recruitert.services;

import lombok.RequiredArgsConstructor;
import org.recruitert.models.PostingSource;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class PostingUpdateService {
    private final PostingRefreshService refreshService;
    private final ExecutorService updateExecutor;

    public void updateAllSources() {
        for (final PostingSource source : PostingSource.values()) {
            updateExecutor.submit(() -> refreshService.updateJobs(source));
        }
    }
}