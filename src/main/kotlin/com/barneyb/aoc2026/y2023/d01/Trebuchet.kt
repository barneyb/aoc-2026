package com.barneyb.aoc2026.y2023.d01

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context

class Trebuchet : Solver<Int, Any>() {

    override fun solveInternal(ctx: Context) = ctx
        .parse { it.lines().toList() }
        .solveA(::solveA)

    fun solveA(lines: List<String>) = lines.sumOf(::extractDigits)

    private fun extractDigits(line: String): Int {
        val first = line.first(Char::isDigit).digitToInt()
        val last = line.last(Char::isDigit).digitToInt()
        return first * 10 + last
    }
}

fun main() {
    Trebuchet().solveAocd()
}
