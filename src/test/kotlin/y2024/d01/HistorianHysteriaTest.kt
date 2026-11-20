package y2024.d01

import com.barneyb.aoc2026.y2024.d01.HistorianHysteria
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HistorianHysteriaTest {

    @Test
    fun partA() {
        val input = "3   4\n" +
                "4   3\n" +
                "2   5\n" +
                "1   3\n" +
                "3   9\n" +
                "3   3\n"

        assertEquals(11L,
                     HistorianHysteria().solveA(input))
    }
}
