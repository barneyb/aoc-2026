package com.barneyb.aoc2026

import com.barneyb.aoc2026.cmd.Context
import com.barneyb.aoc2026.cmd.WithResult
import java.io.BufferedReader

private val RE_YEAR: Regex = "(y(ear)?|aoc)\\d{4}".toRegex()
private val RE_DAY: Regex = "d(ay)?\\d{2}".toRegex()

abstract class Solver<A : Any, B : Any> {

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

    abstract fun solveInternal(ctx: Context): WithResult<out A, out B>

    @Suppress("unused")
    fun solveString(input: String) = input.reader()
        .buffered()
        .use(::solveBare)

    @Suppress("unused")
    fun solveAocd() = withAocdInput(year, day, ::solveBare)

    private fun solveBare(reader: BufferedReader) = solveInternal(Context(reader))
        .done()

}
