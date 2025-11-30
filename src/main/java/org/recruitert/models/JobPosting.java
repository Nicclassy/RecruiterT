package org.recruitert.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String url;
    private PostingState state = PostingState.DEFAULT;

    @Embedded
    @NonNull
    private PostingOrExpiryDate postingDate;
    @Embedded
    @NonNull
    private PostingOrExpiryDate expiryDate;
    @JsonIgnore
    @Getter(lazy = true)
    private final LocalDateTime expiryTime = expiryDate.getExpiryTime();

    @ElementCollection(targetClass = PostingSource.class)
    @Enumerated(EnumType.STRING)
    @NonNull
    private List<PostingSource> sources;

    public static JobPosting from(final JobPostingExtractor extractor) {
        return new JobPosting(
            extractor.title(),
            extractor.url(),
            extractor.postingDate(),
            extractor.expiryDate(),
            extractor.sources()
        );
    }

    public boolean hasExpired() {
        return expiryDate.getValue().hasExpired();
    }
}