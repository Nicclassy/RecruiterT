package org.recruitert.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.NonNull;
import org.recruitert.models.TemporalValue;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeParseException;

@Component
@Converter(autoApply = true)
public class TemporalValueConverter implements AttributeConverter<TemporalValue, String> {
    private static final String DISTANT = "distant";

    @Override
    public String convertToDatabaseColumn(final @NonNull TemporalValue attribute) {
        return switch (attribute) {
            case final TemporalValue.Time time -> time.value().toString();
            case final TemporalValue.Date date -> date.value().toString();
            case final TemporalValue.Ago ago -> ago.value().toString();
            case final TemporalValue.Distant _ -> DISTANT;
        };
    }

    @Override
    public TemporalValue convertToEntityAttribute(final @NonNull String dbData) {
        if (dbData.equals(DISTANT)) return new TemporalValue.Distant();

        try {
            final Period period = Period.parse(dbData);
            return new TemporalValue.Ago(period);
        } catch (final DateTimeParseException ignored) {}

        try {
            final LocalDateTime time = LocalDateTime.parse(dbData);
            return new TemporalValue.Time(time);
        } catch (final DateTimeParseException ignored) {}

        final LocalDate date = LocalDate.parse(dbData);
        return new TemporalValue.Date(date);
    }
}