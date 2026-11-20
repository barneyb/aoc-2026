package com.barneyb.aoc2026.cmd

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.withAocdInput
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.CoreCliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import java.time.Duration
import kotlin.time.toKotlinDuration

class Solve : CoreCliktCommand() {
    val year by argument(help = "Year to solve").int()
    val day by argument(help = "Day to solve").int()
    val pipe by option("-p", "--pipe", help = "Read input from STDIN, rather than using `aocd`.").flag()

    override fun help(context: Context) = "Run a specific solver, then print its answers and timing info.\n" +
            "\n" +
            "Sample Output (tabs after colons):\n" +
            "  Part A:\t3\n" +
            "  Part B:\t9\n" +
            "  Nanos :\t797166\n" +
            "  Time  :\t797.166us"

    override fun run() {
        run(year, day)
    }

    fun run(year: Int, day: Int) {
        val solver = findSolvers().find { it.year == year && it.day == day }
            ?: throw IllegalArgumentException("No solver for $year day $day found")

        val (result, nanos) = if (pipe) runStdIn(solver)
        else runAocd(solver)

        println("Part A:\t${result.first}")
        println("Part B:\t${result.second}")
        println("Nanos :\t${nanos}")
        println("Time  :\t${Duration.ofNanos(nanos).toKotlinDuration()}")
    }

    private fun runStdIn(solver: Solver) = System.`in`.bufferedReader().use {
        // give it up to a second to start
        for (ignored in 1..5) {
            if (it.ready()) break
            Thread.sleep(200)
        }
        if (!it.ready()) throw IllegalStateException("No input found on STDIN?!")

        withNanos { solver.solve(it) }
    }

    private fun runAocd(solver: Solver) = solver.withAocdInput {
        withNanos { solver.solve(it) }
    }
}
