package org.dark0ghost.lab1.test

import org.dark0ghost.lab1.isPrime
import org.junit.jupiter.api.Test

class TestIsPtime {
    private val testArray: List<Int> = "2  3  5  7  11  13  17  19  23  29  31  37  41  43  47  53  59  61  67  71  73  79  83  89  97".split("  ").map {
        it.toInt()
    }

    @Test
    fun testIsPtime(){
        for(i in testArray){
            assert(isPrime(i))
        }
    }


}