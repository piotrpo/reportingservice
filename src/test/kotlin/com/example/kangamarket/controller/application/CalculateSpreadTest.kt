package com.example.kangamarket.controller.application

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.round

class CalculateSpreadTest {
    @Test
    fun calculateSpread() {
        val spread = calculateSpread(4.2610, 4.5997)

        /**
         * taken from requirement:
         *  (4,5997- 4,2610) / [0,5*( 4,5997+
         * 4,2610)] *100% = 0,3387 / [0,5*8,8607] *100% = (0,3387 / 4,4304) *100% = 7,64%
         */
        assertEquals(764, round(spread*100).toInt())
    }

}