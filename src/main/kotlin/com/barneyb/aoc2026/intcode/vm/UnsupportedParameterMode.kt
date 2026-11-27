package com.barneyb.aoc2026.intcode.vm

data class UnsupportedParameterMode(val mode: Int) : IllegalStateException("Unuspported parameter mode: $mode")
