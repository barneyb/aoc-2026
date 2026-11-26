package com.barneyb.aoc2026.intcode.vm

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class VMTest {

    @Test
    fun opcodesOneandTwo() {
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
}
