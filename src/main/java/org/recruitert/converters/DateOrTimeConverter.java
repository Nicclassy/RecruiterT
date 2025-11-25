package org.recruitert.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.NonNull;
import org.recruitert.models.DateOrTime;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Component
@Converter(autoApply = true)
public class DateOrTimeConverter implements AttributeConverter<DateOrTime, String> {
    @Override
    public String convertToDatabaseColumn(final @NonNull DateOrTime attribute) {
        return switch (attribute) {
            case final DateOrTime.Date date -> date.value().toString();
            case final DateOrTime.Time time -> time.value().toString();
        };
    }

    @Override
    public DateOrTime convertToEntityAttribute(final @NonNull String dbData) {
        try {
            final LocalDateTime value = LocalDateTime.parse(dbData);
            return new DateOrTime.Time(value);
        } catch (final DateTimeParseException e) {
            final LocalDate value = LocalDate.parse(dbData);
            return new DateOrTime.Date(value);
        }
    }
}