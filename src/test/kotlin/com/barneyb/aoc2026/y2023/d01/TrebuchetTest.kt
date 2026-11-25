package com.barneyb.aoc2026.y2023.d01

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class TrebuchetTest {

    private val solver = Trebuchet()

    @ParameterizedTest
    @CsvSource("1abc2, 12",
               "pqr3stu8vwx, 38",
               "a1b2c3d4e5f, 15",
               "treb7uchet, 77")
    fun numeric(line: String, value: Int) {
        assertEquals(value,
                     solver.extractDigitsA(line))
    }

    @ParameterizedTest
    @CsvSource(
        "two1nine, 29",
        "eightwothree, 83",
        "eightwo, 82",
        "1dddeightwodd, 12",
        "abcone2threexyz, 13",
        "xtwone3four, 24",
        "4nineeightseven2, 42",
        "zoneight234, 14",
        "7pqrstsixteen, 76",
        "7, 77",
        "seven, 77",
    )
    fun numericOrSpelled(line: String, value: Int) {
        assertEquals(value,
                     solver.extractDigitsB(line))
    }
}
