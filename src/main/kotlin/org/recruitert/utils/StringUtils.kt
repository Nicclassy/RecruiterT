@file:JvmName("StringUtils")
package org.recruitert.utils

import java.net.URI

@JvmOverloads
fun concatenate(vararg args: Any?, separator: String = " ") =
    args.joinToString(separator) { it.toString() }

fun relativeUrlToAbsolute(baseUrl: String, relativeUrl: String) =
    URI.create(baseUrl).resolve(relativeUrl).toString()