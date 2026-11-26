package com.barneyb.aoc2026.y2019.d02

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context
import com.barneyb.aoc2026.intcode.vm.VM

class _1202ProgramAlarm : Solver<Any, Any>() {

    override fun solveInternal(ctx: Context) = ctx
        .parse {
            it.readText()
                .split(",")
                .map { it.trim().toInt() }
        }
        .solveA(::solveA)

    fun solveA(program: List<Int>) = VM().run {
        load(program)
        write(1, 12)
        write(2, 2)
        execute()
        read(0)
    }
}

fun main() {
    _1202ProgramAlarm().solveAocd()
}
