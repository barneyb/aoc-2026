package com.barneyb.aoc2026

import com.barneyb.aoc2026.cmd.List
import com.barneyb.aoc2026.cmd.Solve
import com.github.ajalt.clikt.core.CoreCliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands

class Main : CoreCliktCommand() {
    override fun run() {
    }
}

fun main(args: Array<String>) = Main()
    .subcommands(List(), Solve())
    .main(args)
