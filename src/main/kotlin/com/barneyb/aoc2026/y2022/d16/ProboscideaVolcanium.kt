package com.barneyb.aoc2026.y2022.d16

import com.barneyb.aoc2026.SolveA
import java.io.BufferedReader

private const val START = "AA"
private const val MINUTES = 30

class ProboscideaVolcanium : SolveA() {
    override fun solveA(input: BufferedReader): Int {
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

        // finally, do the walk
        return doWalk(byName.values.associateBy(Valve::name,
                                                Valve::flowRate),
                      graph)
    }

    private fun doWalk(
        flowRates: Map<String, Int>,
        graph: Map<String, Map<String, Int>>,
    ): Int {
        var best = State()
        val queue = ArrayDeque<State>()
        queue.add(best)
        while (queue.isNotEmpty()) {
            val curr = queue.removeFirst()
            if (curr.released > best.released) best = curr
            val adj = graph[curr.at]!!
            adj.forEach { (to, min) ->
                if (curr.canMove(to, min)) {
                    queue += curr.moveTo(to, min, flowRates[to]!!)
                }
            }
        }
        return best.released
    }
}

private data class State(
    val at: String = START,
    val minutesLeft: Int = MINUTES,
    val released: Int = 0,
    val visited: Set<String> = setOf(START),
) {
    fun canMove(to: String, min: Int) = minutesLeft - min >= 1 && to !in visited

    fun moveTo(to: String, min: Int, flowRate: Int): State {
        val left = minutesLeft - min - 1
        return State(
            to,
            left,
            released + left * flowRate,
            visited + to
        )
    }
}
