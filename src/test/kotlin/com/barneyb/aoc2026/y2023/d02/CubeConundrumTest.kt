package com.barneyb.aoc2026.y2023.d02

import kotlin.test.Test
import kotlin.test.assertEquals

class CubeConundrumTest {

    @Test
    fun parse() {
        assertEquals(Game(1, listOf(
            Turn(4, 0, 3),
            Turn(1, 2, 6),
            Turn(0, 2, 0))),
                     Game.from("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"))
    }
}
