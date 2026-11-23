package com.barneyb.aoc2026.y2017.d01

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context

class InverseCaptcha : Solver<Int, Int>() {

    override fun solveInternal(ctx: Context) = ctx
        .parse { it.readLine().toCharArray() }
        .solveA(::solveA)
        .solveB(::solveB)

    fun solveA(chars: CharArray) = walk(chars, chars.size - 1)

    fun solveB(chars: CharArray) = walk(chars, chars.size / 2)

    private fun walk(chars: CharArray, otherIndex: Int): Int {
        var oi = otherIndex
        var sum = 0
        for (c in chars) {
            if (chars[oi % chars.size] == c) {
                sum += c.digitToInt()
            }
            oi += 1
        }
        return sum
    }
}

fun main() {
    InverseCaptcha().solveAocd()
}
