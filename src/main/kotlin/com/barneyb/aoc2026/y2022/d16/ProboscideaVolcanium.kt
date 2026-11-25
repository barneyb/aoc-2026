package com.barneyb.aoc2026.y2022.d16

import com.barneyb.aoc2026.Solver
import com.barneyb.aoc2026.cmd.Context
import com.barneyb.aoc2026.cmd.Logger
import kotlinx.collections.immutable.toPersistentSet
import java.io.BufferedReader

private const val START = "AA"

private const val MINUTES = 30

private const val MINUTES_WITH_ELEPHANT = 26

data class Model(
    val graph: Map<String, Map<String, Int>>,
    val flowRates: Map<String, Int>,
)

class ProboscideaVolcanium : Solver<Int, Any>() {
    override fun solveInternal(ctx: Context) =
        ctx.parse(::parse)
            .solveA { doWalk(it, MINUTES) }
            .solveB { bestSubsets(it, ctx) }


    private fun parse(reader: BufferedReader, logger: Logger): Model {
        val byName = reader.lineSequence()
            .map(Valve::from)
            .associateBy(Valve::name)
        byName[START].also {
            if (it == null) throw IllegalArgumentException("No '$START' room found?!")
            else if (it.canOpen) throw IllegalArgumentException("Valve at '$START' has flow?!")
        }
        logger.log("by Name")

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
        logger.log("Floyd–Warshall")

        // next, build the complete subgraph of useful valves (incl AA)
        val useful = byName.values
            .stream()
            .filter { it.canOpen || it.name == START }
            .map { it.name }
            .toList()
        val graph = mutableMapOf<String, MutableMap<String, Int>>()
        for (i in useful) {
            val adj = graph.computeIfAbsent(i) { mutableMapOf() }
            val id = dist[i]!!
            for (j in useful) {
                // never need to go back to the start
                if (i == j || j == START) continue
                adj[j] = id[j]!!
            }
        }
        logger.log("graph (N=${graph.size})")

        val flowRates = useful.associateWith { byName[it]!!.flowRate }
        logger.log("flow rates")

        return Model(graph, flowRates)
    }

    private fun doWalk(
        model: Model,
        minutes: Int,
    ): Int {
        val (graph, flowRates) = model
        var best = State(minutes,
                         flowRates.asSequence()
                             .filter { it.key in graph }
                             .sumOf { it.value })
        val queue = ArrayDeque<State>()
        queue.add(best)
        while (queue.isNotEmpty()) {
            val curr = queue.removeFirst()
            if (curr.released > best.released) best = curr
            else if (curr.maxPossible < best.maxPossible) continue

            graph[curr.at]!!
                .forEach { (to, min) ->
                    if (to in graph && curr.canMove(to, min)) {
                        queue.addFirst(curr.moveTo(to, min, flowRates[to]!!))
                    }
                }
        }
        return best.released
    }

    private fun bestSubsets(
        model: Model,
        logger: Logger,
    ): Int {
        val (graph, flowRates) = model
        val subsets = mutableListOf(listOf<String>())
        for (k in graph.keys) {
            if (k == START) continue
            subsets.addAll(subsets.map { it + k })
        }
        logger.log("Subsets (${subsets.size})")

        val stats = mutableMapOf<Set<String>, Stat>()
        // skip the empty and full set - that was part A
        for (ss in subsets.subList(1, subsets.size - 1)) {
            val subgraph = graph.toMutableMap()
            ss.forEach(subgraph::remove)
            stats[subgraph.keys.toPersistentSet()] = Stat(
                (ss + START).toPersistentSet(),
                doWalk(Model(subgraph, flowRates), MINUTES_WITH_ELEPHANT)
            )
        }
        logger.log("Stats (${stats.size})")

        var best = -1
        stats.values.forEach {
            val recip = stats[it.reciprocal] ?: throw IllegalArgumentException("No reciprocal of ${it.reciprocal}")
            val total = it.released + recip.released
            if (total > best) best = total
        }

        return best
    }
}

private data class State(
    val minutesLeft: Int,
    val availableFlow: Int,
    val at: String = START,
    val released: Int = 0,
    val visited: Set<String> = setOf(START),
) {
    val maxPossible get() = released + availableFlow * (minutesLeft - 1)

    fun canMove(to: String, min: Int) = minutesLeft - min >= 1 && to !in visited

    fun moveTo(to: String, min: Int, flowRate: Int): State {
        val left = minutesLeft - min - 1
        return State(
            left,
            availableFlow - flowRate,
            to,
            released + left * flowRate,
            visited + to
        )
    }
}

private data class Stat(
    val reciprocal: Set<String>,
    val released: Int,
)
