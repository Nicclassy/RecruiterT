package org.recruitert.services;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.recruitert.models.PostingRefreshTime;
import org.recruitert.models.PostingSource;
import org.recruitert.repositories.PostingRefreshTimeRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostingRefreshTimeService {
    private static final Duration DEFAULT_REFRESH_DURATION = Duration.ofDays(1);
    private static final Duration RETRY_DELAY = Duration.ofMinutes(15);

    private static final List<PostingSource> PERMITTED_SOURCES =
        List.of(PostingSource.WORKDAY_EXTERNAL);

    private final Map<PostingSource, Duration> refreshDurationsBySource =
        Map.of(PostingSource.WORKDAY_EXTERNAL, Duration.ofHours(3));

    private final PostingRefreshTimeRepository refreshTimeRepository;

    public @Nullable PostingRefreshTime tryRefresh(
        final PostingSource source, final Instant now
    ) {
        if (!PERMITTED_SOURCES.contains(source)) {
            return null;
        }

        final Duration refreshDuration =
            refreshDurationsBySource.getOrDefault(source, DEFAULT_REFRESH_DURATION);

        final PostingRefreshTime refreshTime = refreshTimeRepository
            .findById(source)
            .orElseGet(() -> refreshTimeRepository.save(new PostingRefreshTime(source)));

        if (!refreshTime.canUpdate(now, refreshDuration, RETRY_DELAY))
            return null;

        refreshTime.markInProgress(now);
        try {
            refreshTimeRepository.saveAndFlush(refreshTime);
            return refreshTime;
        } catch (final OptimisticLockException e) {
            return null;
        }
    }
}
