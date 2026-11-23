package com.barneyb.aoc2026

import java.io.BufferedReader
import java.io.IOException

val DEFAULT_RETRY_DELAYS = listOf<Long>(200, 200, 200, 200, 200)

fun <T> withAocdInput(year: Int, day: Int, work: (BufferedReader) -> T): T =
    ProcessBuilder("aocd", year.toString(), day.toString())
        .start()
        .inputReader()
        .use { it.whenReady(work, "No input from `aocd` - is your token set up correctly?") }

fun <T> BufferedReader.whenReady(
    work: (BufferedReader) -> T,
    noInputMessage: String = "No input found on STDIN?!",
    retryDelays: List<Long> = DEFAULT_RETRY_DELAYS,
): T {
    if (ready()) return work(this)

    // give it a moment
    retryDelays.forEach {
        Thread.sleep(it)
        if (ready()) return work(this)
    }

    throw IOException(noInputMessage)
}
