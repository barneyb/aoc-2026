package com.barneyb.aoc2026.y2017.d01

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context
import java.io.BufferedReader

class InverseCaptcha : Solver<Int, Any>() {

    override fun solveInternal(ctx: Context) = ctx
        .parse(BufferedReader::readLine)
        .solveA(::solveA)

    fun solveA(input: String): Int {
        val chars = input.toCharArray()
        var prev = chars.last()
        var sum = 0
        for (c in chars) {
            if (prev == c) {
                sum += c.digitToInt()
            }
            prev = c;
        }
        return sum
    }
}

fun main() {
    InverseCaptcha().solveAocd()
}
