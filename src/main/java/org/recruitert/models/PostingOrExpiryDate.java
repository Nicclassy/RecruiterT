package org.recruitert.models;

import jakarta.persistence.*;
import lombok.*;
import org.recruitert.converters.TemporalValueConverter;

@Embeddable
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class PostingOrExpiryDate {
    @Column(name = "value")
    @Convert(converter = TemporalValueConverter.class)
    @NonNull
    private TemporalValue value;
}
