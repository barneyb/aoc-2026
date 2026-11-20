package com.barneyb.aoc2026

import java.io.BufferedReader

private val RE_YEAR: Regex = "(y(ear)?|aoc)\\d{4}".toRegex()
private val RE_DAY: Regex = "d(ay)?\\d{2}".toRegex()

abstract class Solver {

    /** The year this solver solves, derived from its package */
    val year by lazy {
        this.javaClass
            .packageName
            .split(".")
            .findLast { RE_YEAR.matches(it) }
            ?.takeLast(4)
            ?.toInt(10)
            ?: throw NoSuchElementException("Could not derive year from the package name: " + this::class.qualifiedName)
    }

    val day by lazy {
        this.javaClass
            .packageName
            .split(".")
            .findLast { RE_DAY.matches(it) }
            ?.takeLast(2)
            ?.toInt(10)
            ?: throw NoSuchElementException("Could not derive day from the package name: " + this::class.qualifiedName)
    }

    abstract fun solve(input: BufferedReader): Pair<Any, Any>

    @Suppress("unused")
    fun solveAocd() = withAocdInput(year, day, this::solve)

    fun solve(input: String) = input.reader().buffered().use(this::solve)
}

abstract class SolveA : Solver() {
    fun solveA(input: String) = solveA(input.reader().buffered())

    abstract fun solveA(input: BufferedReader): Any

    override fun solve(input: BufferedReader): Pair<Any, Any> = Pair(solveA(input), "")
}

abstract class SolveEach : SolveA() {
    fun solveB(input: String) = solveB(input.reader().buffered())

    abstract fun solveB(input: BufferedReader): Any

    override fun solve(input: BufferedReader): Pair<Any, Any> {
        val inStr = input.readText()
        return Pair(solveA(inStr.reader().buffered()),
                    solveB(inStr.reader().buffered()))
    }
}
