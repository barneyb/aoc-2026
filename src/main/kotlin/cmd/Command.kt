package com.barneyb.aoc2026.cmd

import com.barneyb.aoc2026.Main
import com.barneyb.aoc2026.Solver
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import java.lang.reflect.Modifier
import kotlin.system.measureNanoTime

internal fun <T> withNanos(work: () -> T): Pair<T, Long> {
    var result: T? = null
    val nanos = measureNanoTime { result = work() }
    return Pair(result!!, nanos)
}

internal fun findSolvers() =
    Reflections(Main::class.java.packageName, Scanners.SubTypes)
        .getSubTypesOf(Solver::class.java)
        .asSequence()
        .filter { it.modifiers and Modifier.ABSTRACT == 0 }
        .map { it.getDeclaredConstructor().newInstance() }
