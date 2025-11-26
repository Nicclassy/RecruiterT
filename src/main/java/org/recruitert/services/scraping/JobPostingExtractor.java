package org.recruitert.services.scraping;

import org.recruitert.models.PostingOrExpiryDate;
import org.recruitert.models.PostingSource;

import java.util.List;

public interface JobPostingExtractor {
    String title();
    String url();
    PostingOrExpiryDate postingDate();
    PostingOrExpiryDate expiryDate();
    List<PostingSource> sources();
}
