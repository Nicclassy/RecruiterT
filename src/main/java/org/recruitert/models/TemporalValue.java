package org.recruitert.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public sealed interface TemporalValue
    permits TemporalValue.Time, TemporalValue.Date, TemporalValue.Ago, TemporalValue.Distant {
    record Time(LocalDateTime value) implements TemporalValue {}
    record Date(LocalDate value) implements TemporalValue {}
    record Ago(Period value) implements TemporalValue {}
    record Distant() implements TemporalValue {}
}
