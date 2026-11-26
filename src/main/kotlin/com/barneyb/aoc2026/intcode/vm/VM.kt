package com.barneyb.aoc2026.intcode.vm

class VM {

    private val memory: MutableList<Int> = mutableListOf()
    private var ip = 0

    fun load(vararg program: Int) {
        memory.addAll(program.toList())
    }

    fun load(program: List<Int>) {
        memory.addAll(program)
    }

    fun execute() {
        while (true) {
            when (val op = next()) {
                1    -> {
                    val a = read(next())
                    val b = read(next())
                    val c = next()
                    write(c, a + b)
                }
                2    -> {
                    val a = read(next())
                    val b = read(next())
                    val c = next()
                    write(c, a * b)
                }
                99   -> return
                else -> throw UnknownOpCode(op)
            }
        }
    }

    private fun next() = read(ip++)

    fun read(address: Int): Int = memory[address]

    fun write(address: Int, value: Int) {
        memory[address] = value
    }

    fun dump(): List<Int> = memory.toList()
}
