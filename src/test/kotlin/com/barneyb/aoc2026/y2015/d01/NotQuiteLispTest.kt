package com.barneyb.aoc2026.y2015.d01

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class NotQuiteLispTest {

    @ParameterizedTest
    @CsvSource(
        "(()), 0",
        "(()), 0",
        "()(), 0",
        "(((, 3",
        "(()(()(, 3",
        "))(((((, 3",
        "()), -1",
        "))(, -1",
        "))), -3",
        ")())()), -3",
    )
    fun partA(input: String, floor: Int) {
        assertEquals(floor,
                     NotQuiteLisp().solveA(input))
    }

    @ParameterizedTest
    @CsvSource(
        "), 1",
        "()()), 5",

    )
    fun partB(input: String, pos: Int) {
        assertEquals(pos,
                     NotQuiteLisp().solveB(input))
    }
}
