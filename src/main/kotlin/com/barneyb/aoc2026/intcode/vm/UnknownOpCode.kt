package com.barneyb.aoc2026.intcode.vm

data class UnknownOpCode(val code: Int) : IllegalStateException("Unknown opcode: $code")
