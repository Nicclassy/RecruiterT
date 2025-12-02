package org.recruitert.repositories;

import org.recruitert.models.PostingRefreshTime;
import org.recruitert.models.PostingSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRefreshTimeRepository extends JpaRepository<PostingRefreshTime, PostingSource> {}
