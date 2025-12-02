package org.recruitert.models;

import java.util.List;

public interface JobPostingExtractor {
    String title();
    String url();
    PostingOrExpiryDate postingDate();
    PostingOrExpiryDate expiryDate();
    List<PostingSource> sources();
}
