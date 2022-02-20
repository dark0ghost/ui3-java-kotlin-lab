package org.dark0ghost.lab2.point

fun reverseString(s: String): String = s.reversed()


fun isPalindrome(s: String) = reverseString(s) == s