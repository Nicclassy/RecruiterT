@file:JvmName("StringUtils")
package org.recruitert.utils

@JvmOverloads
fun concatenate(vararg args: Any?, separator: String = " ") =
    args.joinToString(separator) { it.toString() }