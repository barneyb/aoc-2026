package com.barneyb.aoc2026.y2017.d01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class InverseCaptchaTest {
    @ParameterizedTest
    @CsvSource(
        "1122, 3",
        "1111, 4",
        "1234, 0",
        "91212129, 9",
    )
    fun solveA(input: String, expected: Int) {
        assertEquals(expected, InverseCaptcha().solveA(input))
    }
}
