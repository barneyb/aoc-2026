package com.barneyb.aoc2026.y2023.d01

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context

class Trebuchet : Solver<Any, Any>() {

    private val RE_DIGIT = "[0-9]|zero|one|two|three|four|five|six|seven|eight|nine".toRegex()

    override fun solveInternal(ctx: Context) = ctx
        .parse { it.lines().toList() }
        .solveA { it.sumOf(::extractDigitsA) }
        .solveB { it.sumOf(::extractDigitsB) }

    fun extractDigitsA(line: String): Int {
        val first = line.first(Char::isDigit).digitToInt()
        val last = line.last(Char::isDigit).digitToInt()
        return first * 10 + last
    }

    fun extractDigitsB(line: String): Int {
        val first = RE_DIGIT.find(line) ?: throw IllegalArgumentException("No digit found in '$line'")

        // Because spelled digits may overlap (e.g, 'sevenine'), can't just
        // findAll the matches, have to scan the last.
        var last = first
        while (true) {
            val next = RE_DIGIT.find(line, last.range.start + 1) ?: break
            last = next
        }

        return first.toInt() * 10 + last.toInt()
    }
}

private fun MatchResult.toInt() = when (value) {
    "zero"  -> 0
    "one"   -> 1
    "two"   -> 2
    "three" -> 3
    "four"  -> 4
    "five"  -> 5
    "six"   -> 6
    "seven" -> 7
    "eight" -> 8
    "nine"  -> 9
    else    -> value.toInt()
}

fun main() {
    Trebuchet().solveAocd()
}
