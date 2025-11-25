package org.recruitert.services.scraping;

import org.recruitert.models.PostingDate;
import org.recruitert.models.PostingSource;

import java.time.LocalDateTime;
import java.util.List;

public interface JobPostingExtractor {
    String title();
    String url();
    PostingDate postingDate();
    LocalDateTime expiryDate();
    List<PostingSource> sources();
}
