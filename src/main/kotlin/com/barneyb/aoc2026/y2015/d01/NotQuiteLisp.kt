package com.barneyb.aoc2026.y2015.d01

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context
import java.io.BufferedReader

class NotQuiteLisp : Solver<Int, Int>() {

    override fun solveInternal(ctx: Context) = ctx
        .parse(BufferedReader::readLine)
        .solveA(::solveA)
        .solveB(::solveB)

    fun solveA(input: String) = input.chars()
        .map(::valueOf)
        .sum()

    fun solveB(input: String): Int {
        var floor = 0
        return 1 + input.chars()
            .map(::valueOf)
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
