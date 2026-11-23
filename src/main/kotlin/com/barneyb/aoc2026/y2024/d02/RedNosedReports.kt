package com.barneyb.aoc2026.y2024.d02

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context

typealias Report = List<Int>

class RedNosedReports : Solver<Any, Any>() {

    override fun solveInternal(ctx: Context) = ctx
        .parse {
            it.lines()
                .map { it.split(" ").map(String::toInt) }
                .toList()
        }
        .solveA { it.count(::isPerfectlySafe) }
        .solveB { it.count(::isSafeWithDamper) }

    fun isPerfectlySafe(report: Report) = indexOfBadDelta(report) < 0

    fun isSafeWithDamper(report: Report): Boolean {
        val i = indexOfBadDelta(report)
        if (i < 0) return true
        val damped = report.toMutableList()
        (-1..1).forEach { d ->
            if (i + d < 0) return@forEach
            val el = damped.removeAt(i + d)
            if (isPerfectlySafe(damped)) return true
            damped.add(i + d, el)
        }
        return false
    }

    private fun indexOfBadDelta(report: Report): Int {
        if (report.size < 2) return -1
        val valid = if (report[0] < report[1])
            1..3
        else -1 downTo -3
        return report.subList(1, report.size)
            .zip(report)
            .map { (j, i) -> j - i }
            .indexOfFirst { it !in valid }
    }

}

fun main() {
    RedNosedReports().solveAocd()
}
