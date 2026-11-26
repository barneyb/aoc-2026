package com.barneyb.aoc2026.intcode.vm

import java.io.BufferedReader

class VM {

    companion object {
        fun parse(reader: BufferedReader): List<Int> =
            parse(reader.readText())

        fun parse(program: String): List<Int> =
            program.split(",")
                .map { it.trim().toInt() }
    }

    private val memory: MutableList<Int> = mutableListOf()
    private val input = ArrayDeque<Int>()
    private val output = ArrayDeque<Int>()
    private var ip = 0
    private var instruction = 0

    fun load(vararg program: Int) {
        memory.addAll(program.toList())
    }

    fun load(program: List<Int>) {
        memory.addAll(program)
    }

    fun stdIn(vararg inputs: Int) {
        stdIn(inputs.toList())
    }

    fun stdIn(inputs: Iterable<Int>) {
        input.addAll(inputs)
    }

    fun stdOut() = output.removeFirst()

    fun stdOutList(): List<Int> {
        val result = output.toList()
        output.clear()
        return result
    }

    fun execute() {
        while (true) {
            when (val op = opcode()) {
                1    -> {
                    val a = param()
                    val b = param()
                    val c = next()
                    write(c, a + b)
                }
                2    -> {
                    val a = param()
                    val b = param()
                    val c = next()
                    write(c, a * b)
                }
                3    -> {
                    val c = next()
                    write(c, input.removeFirst())
                }
                4    -> {
                    val a = param()
                    output.addLast(a)
                }
                5 -> {
                    val a = param()
                    val b = param()
                    if (a != 0) ip = b
                }
                6 -> {
                    val a = param()
                    val b = param()
                    if (a == 0) ip = b
                }
                7 -> {
                    val a = param()
                    val b = param()
                    val c = next()
                    write(c, if (a < b) 1 else 0)
                }
                8 -> {
                    val a = param()
                    val b = param()
                    val c = next()
                    write(c, if (a == b) 1 else 0)
                }
                99   -> return
                else -> throw UnknownOpCode(op)
            }
        }
    }

    private fun opcode(): Int {
        instruction = next()
        val op = instruction % 100; instruction /= 100
        return op
    }

    private fun param(): Int {
        val mode = instruction % 10; instruction /= 10
        return when (mode) {
            0    -> read(next())
            1    -> next()
            else -> throw UnknownParameterMode(mode)
        }
    }

    private fun next() = read(ip++)

    fun read(address: Int): Int = memory[address]

    fun write(address: Int, value: Int) {
        memory[address] = value
    }

    fun dump(): List<Int> = memory.toList()
}
