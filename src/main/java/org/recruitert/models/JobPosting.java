package org.recruitert.models;

import jakarta.persistence.*;
import lombok.*;
import org.recruitert.services.scraping.JobPostingExtractor;

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
    @Embedded
    @NonNull
    private PostingOrExpiryDate postingDate;
    @Embedded
    @NonNull
    private PostingOrExpiryDate expiryDate;

    @ElementCollection(targetClass = PostingSource.class)
    @Enumerated(EnumType.STRING)
    @NonNull
    private List<PostingSource> sources;

    public static JobPosting from(JobPostingExtractor extractor) {
        return new JobPosting(
            extractor.title(),
            extractor.url(),
            extractor.postingDate(),
            extractor.expiryDate(),
            extractor.sources()
        );
    }
}