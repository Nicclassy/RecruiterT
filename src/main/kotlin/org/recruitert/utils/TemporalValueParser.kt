@file:JvmName("TemporalValueParser")
package org.recruitert.utils

import org.recruitert.models.TemporalValue
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

private val RELATIVE_DATE_PATTERN = Regex("""Posted\s+(\d+)(\+?)\s+(\w+)\s+Ago""", RegexOption.IGNORE_CASE)
private val RELATIVE_NAME_PATTERN = Regex("""Posted\s+([A-Za-z]+).*""",  RegexOption.IGNORE_CASE)

private val DATETIME_FORMATTER = DateTimeFormatter.ofPattern(
    "EEEE d MMMM yyyy hh:mm a",
    Locale.ENGLISH
)

private fun parseTemporalValueFallback(postedText: String): TemporalValue? {
    val match = RELATIVE_NAME_PATTERN.matchEntire(postedText) ?: return null
    return when (match.groupValues[1].lowercase()) {
        "today" -> TemporalValue.Date(LocalDate.now())
        "yesterday" -> TemporalValue.Date(LocalDate.now().minusDays(1))
        else -> null
    }
}

@JvmName("parse")
fun parseTemporalValue(postedText: String): TemporalValue {
    if (postedText == "No End Date") return TemporalValue.Distant()

    try {
        val time = LocalDateTime.parse(postedText, DATETIME_FORMATTER)
        return TemporalValue.Time(time)
    } catch (_: DateTimeParseException) {}

    val match = RELATIVE_DATE_PATTERN.matchEntire(postedText)
        ?: return parseTemporalValueFallback(postedText)
        ?: throw IllegalArgumentException("'$postedText' is not in the correct format")
    val (first, second, third) = match.destructured
    val isDate = second.isEmpty()
    val now = LocalDate.now()
    val amount = first.toLong()

    return if (isDate) {
        TemporalValue.Ago(when (val unit = third.trim('s').lowercase()) {
            "day" -> Period.ofDays(amount.toInt())
            "week" -> Period.ofWeeks(amount.toInt())
            "month" -> Period.ofMonths(amount.toInt())
            else -> error("Unknown unit $unit")
        })
    } else {
        TemporalValue.Date(when (val unit = third.trim('s').lowercase()) {
            "day" -> now.minusDays(amount)
            "week" -> now.minusWeeks(amount)
            "month" -> now.minusMonths(amount)
            else -> error("Unknown unit $unit")
        })
    }
}