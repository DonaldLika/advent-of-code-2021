package day2

import assert
import readLines

fun main() {

    fun readCommands(fileName: String): List<Pair<String, Int>> {
        return readLines(fileName).map {
            it.split(" ").zipWithNext().first()
        }.map {
            Pair(it.first, it.second.toInt())
        }
    }

    fun part1(input: List<Pair<String, Int>>): Int {
        val combinations = input.groupBy({ it.first }, { it.second }).mapValues {
            it.value.sum()
        }

        val forward = combinations.getSum("forward")
        val up = combinations.getSum("up")
        val down = combinations.getSum("down")

        return forward * (down - up)
    }

    fun part2(input: List<Pair<String, Int>>): Int {
        var aim = 0
        var horizontal = 0
        var depth = 0

        input.forEach {
            if (it.first == "forward") {
                horizontal += it.second
                depth += aim * it.second
            }

            if (it.first == "up") {
                aim -= it.second
            }

            if (it.first == "down") {
                aim += it.second
            }
        }

        return horizontal * depth
    }

    // example
    val testInput = readCommands("day2/test")
    assert(part1(testInput) == 150)
    assert(part2(testInput) == 900)

    val inputList = readCommands("day2/input")

    val part1Result = part1(inputList)
    val part2Result = part2(inputList)

    println("Result of part 1= $part1Result")
    println("Result of part 2= $part2Result")

}

fun Map<String, Int>.getSum(type: String) = this[type]!!