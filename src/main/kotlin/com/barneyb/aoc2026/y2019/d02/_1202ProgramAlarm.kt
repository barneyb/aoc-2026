package com.barneyb.aoc2026.y2019.d02

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context
import com.barneyb.aoc2026.intcode.vm.Program
import com.barneyb.aoc2026.intcode.vm.VM


class _1202ProgramAlarm : Solver<Any, Any>() {
    private val TARGET: Long = 19690720

    override fun solveInternal(ctx: Context) = ctx
        .parse(VM::parse)
        .solveA(::solveA)
        .solveB(::solveB)

    fun solveA(program: Program) = VM().run {
        load(program)
        write(1, 12)
        write(2, 2)
        execute()
        read(0)
    }

    fun solveB(program: Program): Int {
        for (noun in 0..99)
            for (verb in 0..99)
                if (TARGET == VM().run {
                        load(program)
                        write(1, noun)
                        write(2, verb)
                        execute()
                        read(0)
                    }) return noun * 100 + verb
        throw IllegalStateException("Failed to find a way to compute $TARGET")
    }
}

fun main() {
    _1202ProgramAlarm().solveAocd()
}
