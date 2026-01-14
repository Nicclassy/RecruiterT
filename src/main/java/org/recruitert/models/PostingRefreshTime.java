package org.recruitert.models;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;

@Entity
@Table
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class PostingRefreshTime {
    @Id
    @Enumerated(EnumType.STRING)
    @NotNull
    private PostingSource postingSource;
    @Enumerated(EnumType.STRING)
    private RefreshState state = RefreshState.IDLE;
    @Version
    private long version;

    @Nullable
    private Instant lastSuccessTime;
    @Nullable
    private Instant lastFailureTime;
    @Nullable
    private Instant lastAttemptTime;

    private final static int MAX_CONSECUTIVE_FAILURES = 5;
    private int failureCount = 0;

    public boolean canUpdate(
        final Instant now,
        final Duration refreshInterval,
        final Duration retryDelay
    ) {
        return switch (this.state) {
            case IDLE ->
                lastSuccessTime == null || now.isAfter(lastSuccessTime.plus(refreshInterval));
            case FAILED ->
                lastFailureTime != null && now.isAfter(lastFailureTime.plus(retryDelay));
            case IN_PROGRESS ->
                lastAttemptTime != null && now.isAfter(lastAttemptTime.plus(retryDelay));
            case DISABLED -> false;
        };
    }

    public void markInProgress(final Instant now) {
        this.state = RefreshState.IN_PROGRESS;
        this.lastAttemptTime = now;
    }

    public void markSuccess(final Instant now) {
        this.state = RefreshState.IDLE;
        this.lastSuccessTime = now;
        this.failureCount = 0;
    }

    public void markFailure(final Instant now) {
        this.state = RefreshState.FAILED;
        this.lastFailureTime = now;
        this.failureCount++;
    }

    public void disable() {
        this.state = RefreshState.DISABLED;
    }
}
