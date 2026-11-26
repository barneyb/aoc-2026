package com.barneyb.aoc2026.y2019.d07

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context
import com.barneyb.aoc2026.intcode.vm.VM
import com.barneyb.aoc2026.permutationsOf

class AmplificationCircuit : Solver<Any, Any>() {

    override fun solveInternal(ctx: Context) = ctx
        .parse(VM::parse)
        .solveA(::onceThrough)
        .solveB(::withFeedback)

    fun onceThrough(program: List<Int>): Int {
        return permutationsOf(0..4).maxOf { perm ->
            val signal = perm.fold(0) { acc, phase ->
                VM().run {
                    load(program)
                    stdIn(phase, acc)
                    execute()
                    stdOut()
                }
            }
            signal
        }
    }

    fun withFeedback(program: List<Int>): Int {
        return permutationsOf(5..9).maxOf { perm ->
            val amplifiers = ArrayDeque(perm.map { phase ->
                VM().apply {
                    load(program)
                    stdIn(phase)
                    execute()
                }
            })
            var signal = 0
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
}

fun main() {
    AmplificationCircuit().solveAocd()
}
