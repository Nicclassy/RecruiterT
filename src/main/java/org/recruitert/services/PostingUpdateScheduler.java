package org.recruitert.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class PostingUpdateScheduler {
    public final PostingUpdateService postingUpdateService;

    @Scheduled(fixedDelay = 60_000)
    public void updateJobs() {
        postingUpdateService.updateAllSources();
    }
}