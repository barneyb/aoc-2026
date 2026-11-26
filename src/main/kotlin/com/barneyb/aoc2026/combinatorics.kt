package com.barneyb.aoc2026

/**
 * I return a lists of subsets of the passed iterable's elements. Each subset is
 * represented by a list, and the iterable may contain duplicates. This makes it
 * not very set-like, but allows more general use.
 */
fun <E> subsetsOf(elements: Iterable<E>): List<List<E>> {
    val subsets = mutableListOf(listOf<E>())
    for (e in elements) {
        subsets.addAll(subsets.map { it + e })
    }
    return subsets
}

fun <E> permutationsOf(elements: Iterable<E>): Sequence<List<E>> {
    val list = elements.toMutableList()
    return sequence {
        doPerms(list, 0)
    }
}

private suspend fun <E> SequenceScope<List<E>>.doPerms(
    list: MutableList<E>,
    start: Int,
) {
    if (start == list.size - 1) yield(list.toList())
    else for (i in start until list.size) {
        val t = list[start]
        list[start] = list[i]
        list[i] = t
        doPerms(list, start + 1)
        list[i] = list[start]
        list[start] = t
    }
}
