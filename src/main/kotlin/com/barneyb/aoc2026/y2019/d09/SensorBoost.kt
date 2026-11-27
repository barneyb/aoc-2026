package com.barneyb.aoc2026.y2019.d09

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context
import com.barneyb.aoc2026.intcode.vm.Program
import com.barneyb.aoc2026.intcode.vm.VM

class SensorBoost : Solver<Any, Any>() {

    override fun solveInternal(ctx: Context) = ctx
        .parse(VM::parse)
        .solveA(::boostTest)

    fun boostTest(program: Program) = VM().run {
        load(program)
        stdIn(1)
        execute()
        val output = stdOutList()
        println(output)
        if (output.size != 1) throw IllegalStateException("There's a bug! $output")
        output.last()
    }
}

fun main() {
    SensorBoost().solveAocd()
}
