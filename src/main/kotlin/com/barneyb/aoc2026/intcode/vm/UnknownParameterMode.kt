package com.barneyb.aoc2026.intcode.vm

data class UnknownParameterMode(val mode: Int) : IllegalStateException("Unknown parameter mode: $mode") {
}
