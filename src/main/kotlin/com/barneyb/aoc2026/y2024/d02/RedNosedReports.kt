package com.barneyb.aoc2026.y2024.d02

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context
import java.util.stream.Stream

typealias Report = List<Int>

class RedNosedReports : Solver<Any, Any>() {

    override fun solveInternal(ctx: Context) = ctx
        .parse {
            it.lines()
                .map { it.split(" ").map(String::toInt) }
        }
        .solveA(::solveA)

    fun solveA(input: Stream<Report>) = input
        .filter(::isSafe)
        .count()

    fun isSafe(report: Report): Boolean {
        if (report.size < 2) return true
        val valid = if (report[0] < report[1])
            1.rangeTo(3)
        else (-1).downTo(-3)
        return report.subList(1, report.size)
            .zip(report)
            .map { (a, b) -> a - b }
            .all(valid::contains)
    }
}

fun main() {
    RedNosedReports().solveAocd()
}
