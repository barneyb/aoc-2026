package com.barneyb.aoc2026.y2024.d02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RedNosedReportsTest {

    @ParameterizedTest
    @CsvSource(
        "7:6:4:2:1, true",
        "1:2:7:8:9, false",
        "9:7:6:2:1, false",
        "1:3:2:4:5, false",
        "8:6:4:4:1, false",
        "1:3:6:7:9, true",
    )
    fun isSafe(reportStr: String, expected: Boolean) {
        assertEquals(expected,
                     RedNosedReports().isSafe(reportStr.split(":")
                                                  .map(String::toInt)))
    }
}
