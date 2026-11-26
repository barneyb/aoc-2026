package com.barneyb.aoc2026.y2023.d02

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context

class CubeConundrum : Solver<Any, Any>() {

    override fun solveInternal(ctx: Context) = ctx
        .parse { it.lines().map(Game::from).toList() }
        .solveA(::solveA)

    fun solveA(games: List<Game>) = games.stream()
        .filter { it.turns.all { it.red <= 12 && it.green <= 13 && it.blue <= 14 } }
        .mapToInt { it.num }
        .sum()
}

data class Game(
    val num: Int,
    val turns: List<Turn>,
) {
    companion object {

        // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        fun from(str: String): Game {
            val parts = str.split(":", ";")
            return Game(parts[0].split(" ")[1].toInt(),
                        parts.subList(1, parts.size).map(Turn::from))
        }
    }
}

data class Turn(
    val red: Int,
    val green: Int,
    val blue: Int,
) {
    companion object {

        // 3 blue, 4 red
        fun from(str: String): Turn {
            var r = 0
            var g = 0
            var b = 0
            str.split(",").forEach {
                val (n, c) = it.trim().split(" ")
                when (c) {
                    "red"   -> r = n.toInt()
                    "green" -> g = n.toInt()
                    "blue"  -> b = n.toInt()
                    else    -> throw IllegalArgumentException("Unknown color '$c'")
                }
            }
            return Turn(r, g, b)
        }
    }
}

fun main() {
    CubeConundrum().solveAocd()
}
