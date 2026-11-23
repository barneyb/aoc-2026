package com.barneyb.aoc2026.cmd

import com.barneyb.aoc2026.Main
import com.barneyb.aoc2026.Solver
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import java.lang.reflect.Modifier

internal fun findSolvers(): Sequence<Solver<*, *>> =
    Reflections(Main::class.java.packageName, Scanners.SubTypes)
        .getSubTypesOf(Solver::class.java)
        .asSequence()
        .filter { it.modifiers and Modifier.ABSTRACT == 0 }
        .map {
            it.getDeclaredConstructor()
                .newInstance()
        }
