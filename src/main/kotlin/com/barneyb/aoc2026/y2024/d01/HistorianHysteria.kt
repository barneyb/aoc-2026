package com.barneyb.aoc2026.y2024.d01

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context
import kotlin.math.abs

private val WHITESPACE = "\\s+".toRegex()

class HistorianHysteria : Solver<Long, Unit>() {
    override fun solveInternal(ctx: Context) =
        ctx.solveA(::solveA)

    fun solveA(input: String): Long {
        val left = mutableListOf<Long>()
        val right = mutableListOf<Long>()
        input.lineSequence()
            .filter { it.isNotBlank() }
            .forEach { line ->
                val parts = line.split(WHITESPACE)
                left += parts[0].toLong()
                right += parts[1].toLong()
            }
        left.sort()
        right.sort()
        return left.zip(right)
            .sumOf { (l, r) -> abs(l - r) }
    }
}
