package org.dark0ghost.lab1;

import static org.dark0ghost.lab1.PrimeKtKt.isPrime;

public class Primes {
    public static void main(String[] args) {
        for(int i = 2; i < 100; i ++){
            final var result = isPrime(i);
            if(result)
                System.out.print(" " + i + " ");
        }
    }
}
