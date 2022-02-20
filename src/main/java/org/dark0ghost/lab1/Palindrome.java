package org.dark0ghost.lab1;

import org.jetbrains.annotations.NotNull;

import static org.dark0ghost.lab2.point.PolindromeKtKt.isPalindrome;

public class Palindrome {
    public static void main(String @NotNull [] args) {
        for (String s : args) {
            if(isPalindrome(s)) {
                System.out.println(s);
            }
        }
    }
}
