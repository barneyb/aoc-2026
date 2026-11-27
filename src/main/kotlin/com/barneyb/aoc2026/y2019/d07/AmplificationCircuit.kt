package com.barneyb.aoc2026.y2019.d07

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context
import com.barneyb.aoc2026.intcode.vm.Program
import com.barneyb.aoc2026.intcode.vm.VM
import com.barneyb.aoc2026.permutationsOf

class AmplificationCircuit : Solver<Any, Any>() {

    override fun solveInternal(ctx: Context) = ctx
        .parse(VM::parse)
        .solveA(::onceThrough)
        .solveB(::withFeedback)

    fun onceThrough(program: Program) = permutationsOf(0L..4).maxOf { perm ->
        val signal = perm.fold(0L) { acc, phase ->
            VM().run {
                load(program)
                stdIn(phase, acc)
                execute()
                stdOut()
            }
        }
        signal
    }

    fun withFeedback(program: Program) = permutationsOf(5L..9).maxOf { perm ->
        val amplifiers = ArrayDeque(perm.map { phase ->
            VM().apply {
                load(program)
                stdIn(phase)
                execute()
            }
        })
        var signal = 0L
        while (amplifiers.isNotEmpty()) {
            amplifiers.removeFirst().apply {
                stdIn(signal)
                signal = stdOut()
                if (running) amplifiers.addLast(this)
            }
        }
        signal
    }
}

fun main() {
    AmplificationCircuit().solveAocd()
}
