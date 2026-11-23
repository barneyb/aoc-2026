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
        assertEquals(expected, InverseCaptcha().solveA(input.toCharArray()))
    }

    @ParameterizedTest
    @CsvSource(
        "1212, 6",
        "1221, 0",
        "123425, 4",
        "123123, 12",
        "12131415, 4",
    )
    fun solveB(input: String, expected: Int) {
        assertEquals(expected, InverseCaptcha().solveB(input.toCharArray()))
    }
}
