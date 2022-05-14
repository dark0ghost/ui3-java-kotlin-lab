package org.dark0ghost.lab_7_8.parser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParseTest {

    @Test
    fun testParseUrl() {
        val result = getATag("<html><head></head><body><a href=\"https://google.com\">text</a></body></html>")
        assertEquals(result, listOf("https://google.com"))
    }
}