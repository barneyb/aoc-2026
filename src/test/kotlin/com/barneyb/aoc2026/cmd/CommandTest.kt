package com.barneyb.aoc2026.cmd

import com.github.ajalt.clikt.core.main
import org.junit.jupiter.api.Test

class CommandTest {

    @Test
    fun list() {
        List().main(arrayOf())
    }

    @Test
    fun solve() {
        Solve().main(
            arrayOf("2015", "1"))
    }
}
