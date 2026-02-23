package org.recruitert.models;

public interface JobPostingExtractor {
    String title();
    String url();
    PostingKind kind();
    PostingOrExpiryDate postingDate();
    PostingOrExpiryDate expiryDate();
    PostingSource source();
}
