package org.recruitert.services.scraping;

import org.recruitert.models.PostingSource;

import java.time.LocalDateTime;
import java.util.List;

public interface JobPostingExtractor {
    String title();
    String url();
    LocalDateTime postingDate();
    LocalDateTime expiryDate();
    List<PostingSource> sources();
}
