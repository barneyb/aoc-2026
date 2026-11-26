package com.barneyb.aoc2026.intcode.vm

import java.io.BufferedReader
import java.lang.Math.toIntExact

typealias Program = List<Long>

class VM {

    companion object {
        fun parse(reader: BufferedReader): List<Long> =
            parse(reader.readText())

        fun parse(program: String): List<Long> =
            program.split(",")
                .map { it.trim().toLong() }
    }

    private data class OnInput(val c: Int)

    private val memory: MutableList<Long> = mutableListOf()
    private val input = ArrayDeque<Long>()
    private var onInput: OnInput? = null
    private val output = ArrayDeque<Long>()
    private var ip = 0
    private var instruction = 0
    private var _halted = false

    val running get() = !_halted

    fun load(vararg program: Long) {
        memory.addAll(program.toList())
    }

    fun load(program: Program) {
        memory.addAll(program)
    }

    fun stdIn(vararg inputs: Int) = stdIn(inputs.map { it.toLong() })

    fun stdIn(vararg inputs: Long) = stdIn(inputs.toList())

    fun stdIn(inputs: Iterable<Long>) {
        input.addAll(inputs)
        onInput.also {
            if (it != null && input.isNotEmpty()) {
                onInput = null
                write(it.c, input.removeFirst())
                execute()
            }
        }
    }

    fun stdOut(): Long {
        if (output.isEmpty()) throw NoOutputAvailable()
        return output.removeFirst()
    }

    fun stdOutList(): List<Long> {
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
                    val c = toIntExact(next())
                    if (input.isEmpty()) {
                        onInput = OnInput(c)
                        break
                    }
                    write(c, input.removeFirst())
                }
                4    -> {
                    val a = param()
                    output.addLast(a)
                }
                5 -> {
                    val a = param()
                    val b = param()
                    if (a != 0L) ip = b.toInt()
                }
                6 -> {
                    val a = param()
                    val b = param()
                    if (a == 0L) ip = b.toInt()
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
                99   -> {
                    _halted = true
                    break
                }
                else -> throw UnknownOpCode(op)
            }
        }
    }

    private fun opcode(): Int {
        instruction = toIntExact(next())
        val op = instruction % 100; instruction /= 100
        return op
    }

    private fun param(): Long {
        val mode = instruction % 10; instruction /= 10
        return when (mode) {
            0    -> read(next())
            1    -> next()
            else -> throw UnknownParameterMode(mode)
        }
    }

    private fun next() = read(ip++)

    fun read(address: Int) = memory[address]

    private fun read(address: Long) = read(toIntExact(address))

    fun write(address: Int, value: Int) = write(address, value.toLong())

    fun write(address: Int, value: Long) {
        memory[address] = value
    }

    private fun write(address: Long, value: Long) = write(toIntExact(address), value)

    fun dump(): List<Long> = memory.toList()
}
