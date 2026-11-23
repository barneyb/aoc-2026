package com.barneyb.aoc2026.cmd

import java.io.BufferedReader
import java.io.PrintStream
import kotlin.time.TimeMark
import kotlin.time.TimeSource

private fun now() = TimeSource.Monotonic.markNow()

enum class Type {
    PARSE,
    PART_A,
    PART_B,
    NANOS,
    TIME,
    OTHER,
}

private val MAX_TYPE_LEN: Int = Type.entries.maxOf { it.name.length }

interface Logger {
    val out: PrintStream
    val start: TimeMark
    var prev: TimeMark

    fun logBare(type: Type, message: Any) {
        out.println("${type.name.padEnd(MAX_TYPE_LEN)}:\t$message")
    }

    fun log(type: Type, message: Any) {
        val step = prev.elapsedNow()
        prev = now()
        val total = start.elapsedNow()
        out.println("${type.name.padEnd(6)}:\t$message\t$step\t$total")
    }

    @Suppress("unused")
    fun log(message: Any) = log(Type.OTHER, message)
}

interface SolveA<M : Any, A : Any, B : Any> {
    fun <Alt : Any> solveA(solver: (M) -> Alt): WithA<M, Alt, B>
}

interface SolveB<M : Any, A : Any, B : Any> {
    fun <Alt : Any> solveB(solver: (M) -> Alt): WithB<M, A, Alt>
}

interface NoResults<M : Any, A : Any, B : Any> : SolveA<M, A, B>, SolveB<M, A, B> {
    fun <AltA : Any, AltB : Any> solveBoth(solver: (M) -> Pair<AltA, AltB>): WithResult<AltA, AltB>
}

interface WithResult<A : Any, B : Any> {
    fun done(): Pair<A, B>
}

interface WithA<M : Any, A : Any, B : Any> : SolveB<M, A, B>, WithResult<A, B>

interface WithB<M : Any, A : Any, B : Any> : SolveA<M, A, B>, WithResult<A, B>

data class Context(
    val input: BufferedReader,
    override val out: PrintStream = System.out,
    override val start: TimeMark = now(),
    override var prev: TimeMark = start,
) : Logger {

    @Suppress("unused")
    fun <A : Any> solveA(solver: (String) -> A) = read().solveA(solver)

    @Suppress("unused")
    fun <B : Any> solveB(solver: (String) -> B) = read().solveB(solver)

    @Suppress("unused")
    fun <A : Any, B : Any> solveBoth(solver: (String) -> Pair<A, B>) = read().solveBoth(solver)

    @Suppress("unused")
    fun <M : Any> parse(parser: (BufferedReader, Logger) -> M) = parsed(parser(input, this))

    @Suppress("unused")
    fun <M : Any> parse(parser: (BufferedReader) -> M) = parsed(parser(input))

    private fun <M : Any> parsed(model: M): NoResults<M, Unit, Unit> {
        log(Type.PARSE, "")
        return ParsedContext(model, out, start, prev, Unit, Unit)
    }

    private fun read() = parse(BufferedReader::readText)
}

data class ParsedContext<M : Any, A : Any, B : Any>(
    val model: M,
    override val out: PrintStream,
    override val start: TimeMark,
    override var prev: TimeMark,
    private var partA: A,
    private var partB: B,
) : Logger, NoResults<M, A, B>, WithA<M, A, B>, WithB<M, A, B> {

    override fun <Alt : Any> solveA(solver: (M) -> Alt): WithA<M, Alt, B> {
        val partA = solver(model)
        log(Type.PART_A, partA)
        return ParsedContext(
            model,
            out,
            start,
            prev,
            partA,
            partB)
    }

    override fun <Alt : Any> solveB(solver: (M) -> Alt): WithB<M, A, Alt> {
        val partB = solver(model)
        log(Type.PART_B, partB)
        return ParsedContext(
            model,
            out,
            start,
            prev,
            partA,
            partB)
    }

    override fun <AltA : Any, AltB : Any> solveBoth(solver: (M) -> Pair<AltA, AltB>): WithResult<AltA, AltB> {
        val (partA, partB) = solver(model)
        log(Type.PART_A, partA)
        logBare(Type.PART_B, partB)
        return ParsedContext(
            model,
            out,
            start,
            prev,
            partA,
            partB)
    }

    override fun done(): Pair<A, B> {
        val elapsed = start.elapsedNow()
        logBare(Type.NANOS, elapsed.inWholeNanoseconds)
        logBare(Type.TIME, elapsed)
        return Pair(partA, partB)
    }
}
