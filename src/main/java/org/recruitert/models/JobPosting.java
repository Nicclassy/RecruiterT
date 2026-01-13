package org.recruitert.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
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
    @NonNull
    private PostingKind kind;

    @Embedded
    @NonNull
    private PostingOrExpiryDate postingDate;
    @Embedded
    @NonNull
    private PostingOrExpiryDate expiryDate;
    @JsonIgnore
    private LocalDateTime expiryTime;

    @ElementCollection(targetClass = PostingSource.class)
    @Enumerated(EnumType.STRING)
    @NonNull
    private List<PostingSource> sources;

    public static JobPosting from(final JobPostingExtractor extractor) {
        final JobPosting posting = new JobPosting(
            extractor.title(),
            extractor.url(),
            extractor.kind(),
            extractor.postingDate(),
            extractor.expiryDate(),
            extractor.sources()
        );
        posting.expiryTime = posting.getExpiryDate().getValue().toLocalDateTime();
        return posting;
    }

    public boolean isSamePostingAs(final JobPosting other) {
        return url.equals(other.url);
    }

    public boolean hasExpired() {
        return expiryDate.getValue().hasPassed();
    }

    public boolean hasExpiredPrematurely() {
        return expiryTime.isBefore(LocalDateTime.now());
    }
}