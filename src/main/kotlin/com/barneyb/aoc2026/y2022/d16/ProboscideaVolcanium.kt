package com.barneyb.aoc2026.y2022.d16

import com.barneyb.aoc2026.Solver
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf
import java.io.BufferedReader

private const val START = "AA"

class ProboscideaVolcanium : Solver() {
    override fun solve(input: BufferedReader): Pair<Int, Int> {
        val byName = input.lineSequence()
            .map(Valve::from)
            .associateBy(Valve::name)

        // first, Floyd–Warshall
        val dist = mutableMapOf<String, MutableMap<String, Int>>()
        for ((name, _, tunnels) in byName.values) {
            val src = dist.computeIfAbsent(name) { mutableMapOf() }
            for (t in tunnels) {
                src[t] = 1
            }
        }
        for (k in byName.values) {
            val kd = dist[k.name]!!
            for (i in byName.values) {
                val id = dist[i.name]!!
                for (j in byName.values) {
                    if (i == j) continue
                    // can't use MAX_VALUE due to overflow
                    val maybe = id.getOrDefault(k.name, 99999999) + kd.getOrDefault(j.name, 99999999)
                    if (maybe < id.getOrDefault(j.name, 99999999)) {
                        id[j.name] = maybe
                    }
                }
            }
        }

        // next, build the complete subgraph of useful valves (plus AA)
        val useful = byName.values
            .stream()
            .filter { it.canOpen || it.name == START }
            .toList()
        val graph = mutableMapOf<String, MutableMap<String, Int>>()
        for (i in useful) {
            val adj = graph.computeIfAbsent(i.name) { mutableMapOf() }
            val id = dist[i.name]!!
            for (j in useful) {
                if (i == j) continue
                adj[j.name] = id[j.name]!!
            }
        }

        // finally, do the walks
        val flowRates = byName.values.associateBy(Valve::name,
                                                  Valve::flowRate)
        return Pair(doWalk(flowRates, graph, 1, 30),
                    doWalk(flowRates, graph, 2, 26))
    }

    private fun doWalk(
        flowRates: Map<String, Int>,
        graph: Map<String, Map<String, Int>>,
        players: Int,
        minutes: Int,
    ): Int {
        var best = State((1..players).map { Player(minutes) })
        val queue = ArrayDeque<State>()
        queue.add(best)
        val closedMemo = mutableMapOf<Set<String>, Int>()
        while (queue.isNotEmpty()) {
            val curr = queue.removeFirst()
            val p = curr.players.maxBy { it.minutesLeft }
            if (curr.released > best.released) best = curr
            else {
                val stillClosed = closedMemo.computeIfAbsent(
                    curr.visited,
                    { viz ->
                        flowRates.asSequence()
                            .filter { it.key !in viz }
                            .sumOf { it.value }
                    })
                if (curr.released + stillClosed * (p.minutesLeft - 1) < best.released) {
                    // opening every remaining valve this minute wouldn't be enough
                    continue
                }
            }
            graph[p.at]!!.forEach { (to, min) ->
                if (curr.canMove(p, to, min)) {
                    queue.addFirst(curr.moveTo(p, to, min, flowRates[to]!!))
                }
            }
        }
        return best.released
    }
}

private data class Player(
    val minutesLeft: Int,
    val at: String = START,
)

private data class State(
    val players: List<Player>,
    val released: Int = 0,
    val visited: PersistentSet<String> = persistentSetOf(START),
) {
    fun canMove(p: Player, to: String, min: Int) = p.minutesLeft - min >= 1 && to !in visited

    fun moveTo(p: Player, to: String, min: Int, flowRate: Int): State {
        val left = p.minutesLeft - min - 1
        return State(
            players.map { if (it === p) Player(left, to) else it },
            released + left * flowRate,
            visited.adding(to)
        )
    }
}
