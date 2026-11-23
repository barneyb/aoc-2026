package com.barneyb.aoc2026.y2024.d01

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context
import java.io.BufferedReader
import kotlin.math.abs

private val WHITESPACE = "\\s+".toRegex()

class HistorianHysteria : Solver<Long, Long>() {
    override fun solveInternal(ctx: Context) =
        ctx
            .parse(::parse)
            .solveA(::solveA)
            .solveB(::solveB)

    private fun parse(input: BufferedReader): Pair<List<Long>, List<Long>> {
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
        return Pair(left, right)
    }

    fun solveA(lists: Pair<List<Long>, List<Long>>): Long {
        val (left, right) = lists
        return left.zip(right)
            .sumOf { (l, r) -> abs(l - r) }
    }

    fun solveB(lists: Pair<List<Long>, List<Long>>): Long {
        val (left, right) = lists
        val hist = right.groupingBy { it }.eachCount()
        return left.map { it * hist.getOrDefault(it, 0) }
            .sum()
    }

}
