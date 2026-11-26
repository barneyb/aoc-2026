package com.barneyb.aoc2026.intcode.vm

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.Test

class VMTest {

    @Test
    fun opcodesOneAndTwo() {
        assertEquals(listOf(
            3500, 9, 10, 70,
            2, 3, 11, 0,
            99,
            30, 40, 50), VM().run {
            load(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50)
            execute()
            dump()
        })
    }

    @Test
    fun onePlusOne() {
        assertEquals(listOf(2, 0, 0, 0, 99), VM().run {
            load(1, 0, 0, 0, 99)
            execute()
            dump()
        })
    }

    @Test
    fun threeTimeTwo() {
        assertEquals(listOf(2, 3, 0, 6, 99), VM().run {
            load(2, 3, 0, 3, 99)
            execute()
            dump()
        })
    }

    @Test
    fun ninetyNineSquared() {
        assertEquals(listOf(2, 4, 4, 5, 99, 9801), VM().run {
            load(2, 4, 4, 5, 99, 0)
            execute()
            dump()
        })
    }

    @Test
    fun fiveTimeSix() {
        assertEquals(listOf(30, 1, 1, 4, 2, 5, 6, 0, 99), VM().run {
            load(1, 1, 1, 4, 99, 5, 6, 0, 99)
            execute()
            dump()
        })
    }

    @Test
    fun inputOutput() {
        assertEquals(42, VM().run {
            load(3, 0, 4, 0, 99)
            stdIn(42)
            execute()
            val result = stdOut()
            val more = stdOutList()
            if (more.isNotEmpty()) throw IllegalStateException("Unexpected output: $more")
            result
        })
    }

    @Test
    fun thirtyThreeTimesImmediateThree() {
        assertEquals(listOf(1002, 4, 3, 4, 99), VM().run {
            load(1002, 4, 3, 4, 33)
            execute()
            dump()
        })
    }

    @Test
    fun oneHundredMinusOne() {
        assertEquals(listOf(1101, 100, -1, 4, 99), VM().run {
            load(1101, 100, -1, 4, 0)
            execute()
            dump()
        })
    }

    @ParameterizedTest
    @CsvSource(
        "7, 0",
        "8, 1",
        "9, 0",
    )
    fun equalsEightPosition(input: Int, expected: Int) {
        assertEquals(expected, VM().run {
            load(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8)
            stdIn(input)
            execute()
            stdOut()
        })
    }

    @ParameterizedTest
    @CsvSource(
        "7, 1",
        "8, 0",
        "9, 0",
    )
    fun lessThanEightPosition(input: Int, expected: Int) {
        assertEquals(expected, VM().run {
            load(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8)
            stdIn(input)
            execute()
            stdOut()
        })
    }

    @ParameterizedTest
    @CsvSource(
        "7, 0",
        "8, 1",
        "9, 0",
    )
    fun equalsEightImmediate(input: Int, expected: Int) {
        assertEquals(expected, VM().run {
            load(3, 3, 1108, -1, 8, 3, 4, 3, 99)
            stdIn(input)
            execute()
            stdOut()
        })
    }

    @ParameterizedTest
    @CsvSource(
        "7, 1",
        "8, 0",
        "9, 0",
    )
    fun lessThanEightImmediate(input: Int, expected: Int) {
        assertEquals(expected, VM().run {
            load(3, 3, 1107, -1, 8, 3, 4, 3, 99)
            stdIn(input)
            execute()
            stdOut()
        })
    }

    @ParameterizedTest
    @CsvSource(
        "-1, 1",
        "0, 0",
        "1, 1",
    )
    fun notEqualsZeroImmediate(input: Int, expected: Int) {
        assertEquals(expected, VM().run {
            load(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1)
            stdIn(input)
            execute()
            stdOut()
        })
    }

    @ParameterizedTest
    @CsvSource(
        "-1, 1",
        "0, 0",
        "1, 1",
    )
    fun notEqualsZeroPosition(input: Int, expected: Int) {
        assertEquals(expected, VM().run {
            load(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9)
            stdIn(input)
            execute()
            stdOut()
        })
    }

    @ParameterizedTest
    @CsvSource(
        "1, 999",
        "7, 999",
        "8, 1000",
        "9, 1001",
        "15, 1001",
    )
    fun partitionAtEight(input: Int, expected: Int) {
        assertEquals(expected, VM().run {
            load(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99,
            )
            stdIn(input)
            execute()
            stdOut()
        })
    }

}
