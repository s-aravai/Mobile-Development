package com.example.tipcalculator

import org.junit.Test

import org.junit.Assert.*
import java.text.NumberFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun amount = 10.00
    val tipPercent = 20.00
    val actualTip = calculateTip(amount, tipPercent, false)
    val expectedTip = NumberFormat.getCurrencyInstance().format(2)
    assertEquals(expectedTip, actualTip)
}



    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}