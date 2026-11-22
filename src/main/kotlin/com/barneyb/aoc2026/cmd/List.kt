package com.barneyb.aoc2026.cmd

import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.CoreCliktCommand

class List : CoreCliktCommand() {
    override fun help(context: Context) = "List available solvers, one per line.\n" +
            "\n" +
            "Sample Output (tabs between fields):\n" +
            "  2026\t01\tMyNiftySolver\n" +
            "  2015\t01\tNotQuiteLisp"

    override fun run() {
        findSolvers().map {
            String.format("%04d\t%02d\t%s", it.year, it.day, it::class.simpleName)
        }
            .sortedDescending()
            .forEach { println(it) }
    }
}
