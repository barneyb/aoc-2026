package com.barneyb.aoc2026.y2024.d01

import com.barneyb.aoc2026.SolveA
import java.io.BufferedReader
import kotlin.math.abs

private val WHITESPACE = "\\s+".toRegex()

class HistorianHysteria : SolveA() {
    override fun solveA(input: BufferedReader): Long {
        val left = mutableListOf<Long>()
        val right = mutableListOf<Long>()
        input.lineSequence()
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
