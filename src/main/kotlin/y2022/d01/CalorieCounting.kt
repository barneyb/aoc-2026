package com.barneyb.aoc2026.y2022.d01

import com.barneyb.aoc2026.Solver
import java.io.BufferedReader

class CalorieCounting : Solver() {

    // It's like a heap, but hard coded to three
    private data class Agg(var a: Long,
                           var b: Long,
                           var c: Long
    ) {
        fun add(n: Long) {
            if (n > c) c = n
            else return
            if (c > b) b = c.also { c = b }
            else return
            if (b > a) a = b.also { b = a }
        }

        fun max() = a
        fun sum() = a + b + c
    }

    override fun solve(input: BufferedReader): Pair<Any, Any> {
        val agg = Agg(0, 0, 0)
        var curr = 0L
        input.lineSequence()
            .forEach { l ->
                if (l == "") {
                    agg.add(curr)
                    curr = 0
                } else {
                    curr += l.toLong()
                }
            }
        if (curr > 0) agg.add(curr)
        return Pair(agg.max(),
                    agg.sum())
    }
}
