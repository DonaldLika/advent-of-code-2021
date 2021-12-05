package day3

import assert
import readLines
import kotlin.math.absoluteValue

fun main() {

    fun part1(input: List<String>): Int {
        val gammaRate = input.toGamaRate()
        val epsilonRate = gammaRate.invertBinaryString()

        return gammaRate.toInt(2) * epsilonRate.toInt(2)
    }

    fun part2(input: List<String>): Int {
        val oxygenGeneratorRating = input.oxygenGeneratorRating()
        val co2ScrubberRating = input.co2ScrubberRating()

        return oxygenGeneratorRating.toInt(2) * co2ScrubberRating.toInt(2)
    }

    // example
    val testInput = readLines("day3/test")

    assert(part1(testInput) == 198)
    assert(part2(testInput) == 230)

    val inputList = readLines("day3/input")

    val part1Result = part1(inputList)
    val part2Result = part2(inputList)

    println("Result of part 1= $part1Result")
    println("Result of part 2= $part2Result")

}

private fun List<String>.toGamaRate(): String = this.first()
    .indices
    .map { index ->
        occurrenceCountPerBitOnIndex(index)
            .maxByOrNull { it.value }?.key!!
    }
    .let { String(it.toCharArray()) }

private fun List<String>.occurrenceCountPerBitOnIndex(index: Int): Map<Char, Int> = this.groupingBy { it[index] }.eachCount()

private fun List<String>.oxygenGeneratorRating(): String {
    return findRating {
        val zeroes = it['0'] ?: 0
        val ones = it['1'] ?: 0
        if (zeroes > ones) '0' else '1'
    }
}

private fun List<String>.co2ScrubberRating(): String {
    return findRating {
        val zeroes = it['0'] ?: 0
        val ones = it['1'] ?: 0
        if (zeroes > ones) '1' else '0'
    }
}

private fun List<String>.findRating(genericRatingSupplier: (fetchedRatings: Map<Char, Int>) -> Char): String {
    var filteredList = this
    for (index in this.first().indices) {
        val most = filteredList.occurrenceCountPerBitOnIndex(index)
        filteredList = filteredList.filter { it[index] == genericRatingSupplier(most) }
        if (filteredList.size == 1) {
            return filteredList.single()
        }
    }
    throw IllegalStateException("Rating couldn't be found")
}

private fun String.invertBinaryString() = this.map { it.digitToInt().minus(1).absoluteValue }.joinToString("")