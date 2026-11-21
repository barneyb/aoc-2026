package com.barneyb.aoc2026.y2022.d01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CalorieCountingTest {

    private var INPUT = "" +
            "1000\n" +
            "2000\n" +
            "3000\n" +
            "\n" +
            "4000\n" +
            "\n" +
            "5000\n" +
            "6000\n" +
            "\n" +
            "7000\n" +
            "8000\n" +
            "9000\n" +
            "\n" +
            "10000"

    @Test
    fun bothParts() {
        assertEquals(Pair(24000L, 45000L),
                     CalorieCounting().solve(INPUT))
    }
}
