package org.recruitert.services.scraping

import com.microsoft.playwright.*
import com.microsoft.playwright.options.WaitForSelectorState

private const val DEFAULT_TIMEOUT = 1000.0
private val DEFAULT_WAIT_FOR_OPTIONS = Locator.WaitForOptions()
    .setState(WaitForSelectorState.ATTACHED)
    .setTimeout(DEFAULT_TIMEOUT)

class LocatorFactory @JvmOverloads constructor(
    val page: Page,
    private val options: Locator.WaitForOptions = DEFAULT_WAIT_FOR_OPTIONS
) {
    @Throws(TimeoutError::class)
    fun locator(vararg selectors: String): Locator {
        val locator = PageLocator(page, options, selectors)
        return locator.locate()
    }

    @Throws(TimeoutError::class)
    fun locator(locator: Locator, vararg selectors: String): Locator {
        val locator = ElementLocator(locator, options, selectors)
        return locator.locate()
    }

    fun tryLocate(locator: Locator, vararg selectors: String): Locator? {
        val locator = ElementLocator(locator, options, selectors)
        return locator.tryLocate()
    }

    fun locatorIfPresent(selector: String): Locator? {
        val locator = page.locator(selector)
        try {
            locator.waitFor(options)
            return locator
        } catch (_: TimeoutError) {
            return null
        }
    }
}