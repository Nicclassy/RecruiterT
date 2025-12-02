package org.recruitert.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostingRefreshTime {
    @Id
    @Enumerated(EnumType.STRING)
    private PostingSource postingSource;
    @NonNull
    private Instant lastRefreshTime;
}
