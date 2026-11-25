package com.barneyb.aoc2026.y2023.d09

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context

class MirageMaintenance : Solver<Int, Any>() {

    override fun solveInternal(ctx: Context) = ctx
        .parse {
            it.lines()
                .map { it.split(" ").map { it.toInt() } }
                .toList()
        }
        .solveA(::solveA)

    fun solveA(histories: List<List<Int>>): Int {
        return histories.map(this::predict)
            .sum()
    }

    fun predict(history: List<Int>): Int {
        if (history.all { it == 0 }) return 0
        val n = predict(history.subList(1, history.size)
                            .zip(history)
                            .map { (a, b) -> a - b })
        return history.last() + n
    }
}

fun main() {
    MirageMaintenance().solveAocd()
}
