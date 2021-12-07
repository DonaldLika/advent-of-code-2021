package day6

import assert
import readLines

var NR_OF_DAYS = 80

fun main() {

    fun part1(fishes: List<Fish>): Long {
        return countTotalFish(fishes)
    }

    fun part2(fishes: List<Fish>): Long {
        return countTotalFish(fishes, 256)
    }

    val testInput = readFishes("day6/test")

    assert(part1(testInput) == 5934L)
    assert(part2(testInput) == 26984457539)

    val input = readFishes("day6/input")

    val part1Result = part1(input)
    val part2Result = part2(input)

    println("Result of part 1= $part1Result")
    println("Result of part 2= $part2Result")
}

fun countTotalFish(fishes: List<Fish>, limit: Int = NR_OF_DAYS): Long {
    var finalFishes = fishes
    repeat(limit) {
        val newFishes = finalFishes.mapNotNull { it.step() }
        finalFishes = finalFishes.plus(newFishes)
    }

    return finalFishes.count().toLong()
}

class Fish(var timer: Int = 8) {
    override fun toString(): String = "[$timer]"
    fun step(): Fish? {
        if (isReadyForCreation()) {
            timer = 6
            return Fish(8)
        }
        timer--
        return null
    }

    private fun isReadyForCreation() = timer == 0
}

private fun readFishes(name: String)= readLines(name)
    .flatMap { it.split(",").map { nr -> nr.toInt() } }
    .map {
        Fish(it)
    }