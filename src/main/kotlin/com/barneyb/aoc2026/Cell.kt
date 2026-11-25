package com.barneyb.aoc2026

interface Cell<T> {
    val value: T
}

data class MutableCell<T>(override var value: T) : Cell<T>
