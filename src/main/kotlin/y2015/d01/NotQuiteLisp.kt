package com.barneyb.aoc2026.y2015.d01

import com.barneyb.aoc2026.SolveEach
import java.io.BufferedReader

class NotQuiteLisp : SolveEach() {

    override fun solveA(input: BufferedReader) = input.readLine()
        .chars()
        .map(this::valueOf)
        .sum()

    override fun solveB(input: BufferedReader): Int {
        var floor = 0
        return 1 + input.readLine()
            .chars()
            .map(this::valueOf)
            .takeWhile {
                floor += it
                floor >= 0
            }
            .count()
            .toInt()
    }

    private fun valueOf(c: Int): Int = when (c) {
        '('.code -> 1
        ')'.code -> -1
        else     -> 0
    }
}
