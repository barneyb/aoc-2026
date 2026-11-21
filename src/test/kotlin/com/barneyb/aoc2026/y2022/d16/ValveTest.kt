package com.barneyb.aoc2026.y2022.d16

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ValveTest {

    @Test
    fun from() {
        assertEquals(Valve("AA", 0, setOf("DD", "II", "BB")),
                     Valve.from("Valve AA has flow rate=0; tunnels lead to valves DD, II, BB"))
        assertEquals(Valve("HH", 22, setOf("GG")),
                     Valve.from("Valve HH has flow rate=22; tunnel leads to valve GG"))
    }
}
