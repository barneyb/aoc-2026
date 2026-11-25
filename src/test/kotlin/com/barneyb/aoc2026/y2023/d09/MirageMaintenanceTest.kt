package com.barneyb.aoc2026.y2023.d09

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MirageMaintenanceTest {

    @Test
    fun solveA() {
        assertEquals(68,
                     MirageMaintenance().predict(listOf(10, 13, 16, 21, 30, 45)))
    }
}
