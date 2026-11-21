package com.barneyb.aoc2026.y2022.d16

data class Valve(
    val name: String,
    val flowRate: Int,
    var tunnels: Set<String>,
) {
    val canOpen get() = flowRate > 0

    companion object {
        fun from(line: String): Valve {
            val words = line
                .replace(",", "")
                .split(" ")
            return Valve(
                words[1],
                words[4]
                    .split("=", ";")[1]
                    .toInt(),
                words.subList(9, words.size)
                    .toSet())
        }
    }
}
