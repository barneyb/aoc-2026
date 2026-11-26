package com.barneyb.aoc2026

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CombinatoricsTest {
    @Test
    fun emptySubset() {
        assertEquals(listOf(listOf<Any>()),
                     subsetsOf(listOf<Any>()))
    }

    @Test
    fun subset() {
        assertEquals(listOf(
            listOf(),
            listOf(1),
            listOf(2), listOf(1, 2),
            listOf(3), listOf(1, 3), listOf(2, 3),
            listOf(1, 2, 3)),
                     subsetsOf(listOf(1, 2, 3)))
    }

    @Test
    fun emptyPerms() {
        assertEquals(listOf<List<Any>>(),
                     permutationsOf(listOf<Any>()).toList())
    }

    @Test
    fun perms() {
        assertEquals(listOf(
            listOf(1, 2, 3), listOf(1, 3, 2),
            listOf(2, 1, 3), listOf(2, 3, 1),
            listOf(3, 2, 1), listOf(3, 1, 2)),
                     permutationsOf(listOf(1, 2, 3)).toList())
    }
}
