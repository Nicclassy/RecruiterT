@file:JvmName("RelativeDateParser")
package org.recruitert.utils

import java.time.LocalDateTime

private val RELATIVE_DATE_PATTERN = Regex("""Posted\s+(\d+)\s+(\w+)\s+Ago""", RegexOption.IGNORE_CASE)

@JvmName("parse")
fun parseRelativeDate(postedText: String): LocalDateTime {
    val match = RELATIVE_DATE_PATTERN.matchEntire(postedText)
        ?: throw IllegalArgumentException("'$postedText' is not in the correct date format")
    val (first, second) = match.destructured
    val now = LocalDateTime.now()
    val amount = first.toLong()

    return when (val unit = second.trim('s').lowercase()) {
        "second" -> now.minusDays(amount)
        "minute" -> now.minusDays(amount)
        "hour" -> now.minusDays(amount)
        "day" -> now.minusDays(amount)
        "week" -> now.minusWeeks(amount)
        "month" -> now.minusMonths(amount)
        else -> throw IllegalArgumentException("Unknown unit $unit")
    }
}