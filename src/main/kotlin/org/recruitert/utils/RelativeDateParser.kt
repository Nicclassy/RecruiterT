@file:JvmName("RelativeDateParser")
package org.recruitert.utils

import org.recruitert.models.DateOrTime
import java.time.LocalDate
import java.time.LocalDateTime

private val RELATIVE_DATE_PATTERN = Regex("""Posted\s+(\d+)\s+(\w+)\s+Ago""", RegexOption.IGNORE_CASE)
private val RELATIVE_NAME_PATTERN = Regex("""Posted\s+([A-Za-z]+).*""",  RegexOption.IGNORE_CASE)

private fun parseRelativeDateFallback(postedText: String): DateOrTime? {
    val match = RELATIVE_NAME_PATTERN.matchEntire(postedText) ?: return null
    if (match.groupValues[1].lowercase() == "yesterday")
        return DateOrTime.Date(LocalDate.now().minusDays(1))

    return null
}

@JvmName("parse")
fun parseRelativeDate(postedText: String): DateOrTime {
    val match = RELATIVE_DATE_PATTERN.matchEntire(postedText)
        ?: return parseRelativeDateFallback(postedText)
        ?: throw IllegalArgumentException("'$postedText' is not in the correct date format")
    val (first, second) = match.destructured
    val now = LocalDateTime.now()
    val amount = first.toLong()

    return DateOrTime.Time(when (val unit = second.trim('s').lowercase()) {
        "second" -> now.minusDays(amount)
        "minute" -> now.minusDays(amount)
        "hour" -> now.minusDays(amount)
        "day" -> now.minusDays(amount)
        "week" -> now.minusWeeks(amount)
        "month" -> now.minusMonths(amount)
        else -> throw IllegalArgumentException("Unknown unit $unit")
    })
}