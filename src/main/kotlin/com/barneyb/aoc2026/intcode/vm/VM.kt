package com.barneyb.aoc2026.intcode.vm

class VM {

    private val store: MutableList<Int> = mutableListOf()

    fun load(vararg program: Int) {
        store.addAll(program.toList())
    }

    fun load(program: List<Int>) {
        store.addAll(program)
    }

    fun execute() {
        var ip = 0
        while (true) {
            val op = read(ip++)
            when (op) {
                1    -> {
                    val a = read(read(ip++))
                    val b = read(read(ip++))
                    val c = read(ip++)
                    write(c, a + b)
                }
                2    -> {
                    val a = read(read(ip++))
                    val b = read(read(ip++))
                    val c = read(ip++)
                    write(c, a * b)
                }
                99   -> return
                else -> throw UnknownOpCode(op)
            }
        }
    }

    fun read(pos: Int): Int = store[pos]

    fun write(pos: Int, value: Int) {
        store[pos] = value
    }

    fun dump(): List<Int> = store.toList()
}
