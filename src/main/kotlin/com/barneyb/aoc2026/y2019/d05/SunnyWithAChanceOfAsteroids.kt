package com.barneyb.aoc2026.y2019.d05

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context
import com.barneyb.aoc2026.intcode.vm.Program
import com.barneyb.aoc2026.intcode.vm.VM

private const val AIR_CONDITIONER: Long = 1
private const val THERMAL_RADIATOR: Long = 5

class SunnyWithAChanceOfAsteroids : Solver<Any, Any>() {

    override fun solveInternal(ctx: Context) = ctx
        .parse(VM::parse)
        .solveA { test(it, AIR_CONDITIONER) }
        .solveB { test(it, THERMAL_RADIATOR) }

    fun test(program: Program, systemId: Long) = VM().run {
        load(program)
        stdIn(systemId)
        execute()
        val out = stdOutList()
        println(out)
        out.last()
    }
}

fun main() {
    SunnyWithAChanceOfAsteroids().solveAocd()
}
