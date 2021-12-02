package day1

import assert
import readIntegerFileInput

fun main() {

    fun part1(input: List<Int>): Int {
        return input.zipWithNext()
            .count {
                it.first < it.second
            }
    }

    fun part2(input: List<Int>): Int {
        return input
            .windowed(3)
            .map { it.sum() }
            .zipWithNext()
            .count { it.second - it.first > 0 }
    }

    // example
    val testInput = readIntegerFileInput("day1/test")
    assert(part1(testInput) == 5)
    assert(part2(testInput) == 3)

    val inputList = readIntegerFileInput("day1/input")

    val part1Result = part1(inputList)
    val part2Result = part2(inputList)

    println("Result of part 1= $part1Result")
    println("Result of part 2= $part2Result")

}
