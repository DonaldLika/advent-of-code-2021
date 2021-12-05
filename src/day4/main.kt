package day4

import assert
import readLines

fun main() {

    fun part1(bingoBoard: Lottery): Int {
        return bingoBoard.firstWinner()
    }

    fun part2(bingoBoard: Lottery): Int {
        return bingoBoard.lastWinner()
    }

    // example
    val testInput = readData("day4/test")

    assert(part1(testInput) == 4512)
    assert(part2(testInput) == 1924)

    val input = readData("day4/input")

    val part1Result = part1(input)
    val part2Result = part2(input)

    println("Result of part 1= $part1Result")
    println("Result of part 2= $part2Result")

}

fun readData(fileName: String): Lottery {
    val lines = readLines(fileName)
    val draw = lines
        .first()
        .split(",").map { v -> v.toInt() }

    val matrices = lines.asSequence().drop(2)
        .filter { it.isNotEmpty() }
        .map {
            it.split("\\s+".toRegex())
                .filter { nr -> nr.isNotBlank() }
                .map { str -> str.toInt() }
        }.chunked(5).map {
            Board(it)
        }.toList()

    return Lottery(draw, matrices)

}

class Board(private val values: List<List<Int>>, var isWinner: Boolean = false) {

    fun verifyDraw(draw: List<Int>): Boolean {
        val completedRow = values.any {
            draw.containsAll(it)
        }

        val completedColumn = values.indices.any {
            draw.containsAll(values.map { v -> v[it] })
        }

        return completedRow || completedColumn
    }

    fun markAsWinner() {
        this.isWinner = true
    }

    fun sumOfUnMarked(draw: List<Int>) = values
        .flatMap { it.toList() }
        .filter { !draw.contains(it) }
        .sum()

}

data class Lottery(private val draws: List<Int>, private val boards: List<Board>) {

    fun firstWinner(): Int {
        for (i in 5 until draws.size) {
            val currentDraws = draws.subList(0, i)
            val firstWinner = announceWinners(currentDraws).firstOrNull()
            if (firstWinner != null) {
                return firstWinner.sumOfUnMarked(currentDraws) * currentDraws.last()
            }
        }

        throw IllegalStateException("Cannot determine the winner")
    }

    fun lastWinner(): Int {
        var winnerMatrix: Board? = null
        var winnerDraws: List<Int>? = null
        for (i in 5..draws.size) {
            val currentDraws = draws.subList(0, i)
            val lastWinner = announceWinners(currentDraws).lastOrNull()
            if (lastWinner != null) {
                winnerMatrix = lastWinner
                winnerDraws = currentDraws
            }
        }

        if (winnerMatrix == null || winnerDraws == null) {
            throw IllegalStateException("Cannot determine the winner")
        }

        return winnerMatrix.sumOfUnMarked(winnerDraws) * winnerDraws.last()
    }

    private fun announceWinners(draw: List<Int>): List<Board> {
        return boards
            .filter { it.isWinner.not() } // was not marked as winner before
            .filter { it.verifyDraw(draw) } // isWinner on current draw
            .map {
                it.markAsWinner()
                it
            }
    }
}