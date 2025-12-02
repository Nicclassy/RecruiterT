package org.recruitert.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public sealed interface TemporalValue
    permits TemporalValue.Time, TemporalValue.Date, TemporalValue.Ago, TemporalValue.Distant {
    default boolean hasPassed() { return false; }
    LocalDateTime toLocalDateTime();

    record Time(LocalDateTime value) implements TemporalValue {
        @Override
        public boolean hasPassed() {
            return LocalDateTime.now().isAfter(value);
        }

        @Override
        public LocalDateTime toLocalDateTime() {
            return value;
        }
    }

    record Date(LocalDate value) implements TemporalValue {
        @Override
        public boolean hasPassed() {
            return LocalDate.now().isAfter(value);
        }

        @Override
        public LocalDateTime toLocalDateTime() {
            return value.atStartOfDay();
        }
    }

    record Ago(Period value) implements TemporalValue {
        @Override
        public LocalDateTime toLocalDateTime() {
            return LocalDateTime.now().minus(value);
        }
    }

    record Distant() implements TemporalValue {
        @Override
        public LocalDateTime toLocalDateTime() {
            return LocalDateTime.MAX;
        }
    }
}
