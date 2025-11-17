package org.recruitert.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;
    private LocalDateTime postingDate;
    private LocalDateTime expiryDate;

    @ElementCollection(targetClass = PostingSource.class)
    @Enumerated(EnumType.STRING)
    private List<PostingSource> sources;
}