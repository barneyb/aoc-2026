package com.barneyb.aoc2026.y2019.d05

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context
import com.barneyb.aoc2026.intcode.vm.VM

class SunnyWithAChanceOfAsteroids : Solver<Any, Any>() {

    override fun solveInternal(ctx: Context) = ctx
        .parse(VM::parse)
        .solveA(::solveA)

    fun solveA(program: List<Int>) = VM().run {
        load(program)
        stdIn(1)
        execute()
        val out = stdOutList()
        println(out)
        out.last()
    }
}

fun main() {
    SunnyWithAChanceOfAsteroids().solveAocd()
}
