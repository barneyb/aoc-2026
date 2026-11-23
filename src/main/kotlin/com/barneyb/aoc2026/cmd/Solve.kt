package com.barneyb.aoc2026.cmd

import com.barneyb.aoc2026.whenReady
import com.barneyb.aoc2026.withAocdInput
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.CoreCliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import java.io.BufferedReader

typealias Work = (BufferedReader) -> Any

class Solve : CoreCliktCommand() {
    val year by argument(help = "Year to solve").int()
    val day by argument(help = "Day to solve").int()
    val pipe by option("-p", "--pipe", help = "Read input from STDIN, rather than calling `aocd`.").flag()

    override fun help(context: Context) = "Run a specific solver, then print its answers and timing info.\n" +
            "\n" +
            "Sample Output (tabs between fields):\n" +
            "\tPARSE :         89.402ms        90.738583ms\n" +
            "\tPART_A: 321     3.360834ms      94.103167ms\n" +
            "\tPART_B: 1234    1.082042ms      95.186667ms\n" +
            "\tNANOS : 95225375\n" +
            "\tTIME  : 95.225375ms"

    override fun run() {
        run(year, day)
    }

    fun run(year: Int, day: Int) {
        val solver = findSolvers().find { it.year == year && it.day == day }
            ?: throw IllegalArgumentException("No solver for $year day $day found")

        val work: Work = {
            solver.solveInternal(Context(it))
                .done()
        }

        if (pipe) System.`in`.bufferedReader().use { it.whenReady(work) }
        else withAocdInput(this.year, this.day, work)
    }

}
