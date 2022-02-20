package org.dark0ghost.lab1.test

import org.dark0ghost.lab2.point.isPalindrome
import org.junit.jupiter.api.Test

class PalindromeTest {

    @Test
    fun testPalindrome() {
        val testPalindrome1 = "lol"
        assert(isPalindrome(testPalindrome1))

        val testPalindrome2 = "121"
        assert(isPalindrome(testPalindrome2))

        val testPalindrome3 = "122"
        assert(!isPalindrome(testPalindrome3))
    }
}