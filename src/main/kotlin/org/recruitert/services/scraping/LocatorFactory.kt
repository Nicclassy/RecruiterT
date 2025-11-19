package org.recruitert.services.scraping

import com.microsoft.playwright.*;

class LocatorFactory(
    private val options: Locator.WaitForOptions,
    val page: Page
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
}