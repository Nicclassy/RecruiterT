@file:JvmName("TextExtractor")
package org.recruitert.utils

private val APPLICATIONS_CLOSE_TEXT = Regex(""".*Applications Close(.*)""", RegexOption.IGNORE_CASE)

fun extractApplicationsCloseText(text: String): String {
    val match = APPLICATIONS_CLOSE_TEXT.matchEntire(text)
        ?: throw IllegalArgumentException("'$text' does not have an applications close value")
    return match.groupValues[1]
}