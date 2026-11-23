package com.barneyb.aoc2026.y2024.d01

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HistorianHysteriaTest {

    @Test
    fun examples() {
        val input = "3   4\n" +
                "4   3\n" +
                "2   5\n" +
                "1   3\n" +
                "3   9\n" +
                "3   3\n"

        assertEquals(Pair(11L, 31L),
                     HistorianHysteria().solveString(input))
    }
}
