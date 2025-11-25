package org.recruitert.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public sealed interface DateOrTime permits DateOrTime.Date, DateOrTime.Time {
    record Date(LocalDate value) implements DateOrTime {}
    record Time(LocalDateTime value) implements DateOrTime {}
}
