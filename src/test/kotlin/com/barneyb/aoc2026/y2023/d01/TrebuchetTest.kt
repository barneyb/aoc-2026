package com.barneyb.aoc2026.y2023.d01

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TrebuchetTest {

    @Test
    fun solveA() {
        assertEquals(142,
                     Trebuchet().solveString(
                         "1abc2\n" +
                                 "pqr3stu8vwx\n" +
                                 "a1b2c3d4e5f\n" +
                                 "treb7uchet")
                         .first)
    }
}
