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
    @Nullable
    private Instant lastSuccessTime;
    @Nullable
    private Instant lastFailureTime;
    @Nullable
    private Instant lastAttemptTime;

    private final static int MAX_CONSECUTIVE_FAILURES = 5;
    private int failureCount = 0;

    public boolean shouldUpdate(final Instant now, final Duration refreshInterval) {
        if (failureCount >= MAX_CONSECUTIVE_FAILURES)
            return false;

        final boolean lastAttemptWasUnsuccessful = lastAttemptTime != null && lastSuccessTime == null;
        if (lastAttemptWasUnsuccessful)
            return false;

        final boolean noPriorAttempts = lastSuccessTime == null;
        if (noPriorAttempts)
            return true;

        return now.isAfter(lastSuccessTime.plus(refreshInterval));
    }

    public void markAttempt(final Instant now) {
        this.lastAttemptTime = now;
    }

    public void markSuccess(final Instant now) {
        this.lastSuccessTime = now;
        this.failureCount = 0;
    }

    public void markFailure(final Instant now) {
        this.lastFailureTime = now;
        this.failureCount++;
    }
}
