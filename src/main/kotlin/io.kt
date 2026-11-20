package com.barneyb.aoc2026

import java.io.BufferedReader

fun <T> withAocdInput(year: Int, day: Int, work: (BufferedReader) -> T): T =
    ProcessBuilder("aocd", year.toString(), day.toString())
        .start()
        .inputReader()
        .use(work)

fun <T> Solver.withAocdInput(work: (BufferedReader) -> T): T =
    withAocdInput(this.year, this.day, work)
