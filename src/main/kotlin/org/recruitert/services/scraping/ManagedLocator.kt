package org.recruitert.services.scraping

import com.microsoft.playwright.*;
import kotlin.jvm.Throws

abstract class ManagedLocator {
    abstract val options: Locator.WaitForOptions
    abstract val elementLocator: (String) -> Locator
    abstract val selectors: Array<out String>

    @Throws(TimeoutError::class)
    fun locate(): Locator {
        for (selector in selectors) {
            val locator = elementLocator(selector)
            try {
                locator.waitFor(options)
            } catch (_: TimeoutError) {
                continue
            }
            return locator
        }

        throw TimeoutError("Could not locate $selectors")
    }
}

class PageLocator(
    private val page: Page,
    override val options: Locator.WaitForOptions,
    override val selectors: Array<out String>
) : ManagedLocator() {

    override val elementLocator: (String) -> Locator = page::locator
}

class ElementLocator(
    private val element: Locator,
    override val options: Locator.WaitForOptions,
    override val selectors: Array<out String>
) : ManagedLocator() {
    override val elementLocator: (String) -> Locator = element::locator
}