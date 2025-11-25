package org.recruitert.models;

import jakarta.persistence.*;
import lombok.*;
import org.recruitert.converters.DateOrTimeConverter;

@Embeddable
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class PostingDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "posting_date")
    @Convert(converter = DateOrTimeConverter.class)
    @NonNull
    private DateOrTime postingDate;
}
