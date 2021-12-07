package day5

import readLines

val NUMBER_EXTRACTOR = "\\d+".toRegex()

fun main() {

    fun part1(segments: List<Segment>): Int {
        val filteredLines = segments.filter { it.from.x == it.to.x || it.from.y == it.to.y }
        return filteredLines.calculateIntersections()
    }

    fun part2(segments: List<Segment>): Int {
        return segments.calculateIntersections()
    }

    val testInput = readSegments("day5/test")

    assert(part1(testInput) == 5)
    assert(part2(testInput) == 12)

    val input = readSegments("day5/input")

    val part1Result = part1(input)
    val part2Result = part2(input)

    println("Result of part 1= $part1Result")
    println("Result of part 2= $part2Result")
}

fun readSegments(fileName: String): List<Segment> {
    return readLines(fileName)
        .map { line ->
            NUMBER_EXTRACTOR.findAll(line).map { it.value.toInt() }.toList()
        }.map {
            Segment(Point(it[0], it[1]), Point(it[2], it[3]))
        }
}

fun List<Segment>.calculateIntersections() = this
    .flatMap { it.coveredPoints() }
    .groupingBy { it }.eachCount()
    .count { it.value >= 2 }

data class Segment(val from: Point, val to: Point) {

    private fun absoluteSegment(): Segment {
        if (from.x < to.x) return this
        if (from.x == to.x && from.y < to.y) return this
        return Segment(to, from)
    }

    private fun withHorizontalPosition(x: Int) = Point(x, from.y)
    private fun withVerticalPosition(y: Int) = Point(from.x, y)

    fun coveredPoints(): List<Point> = with(absoluteSegment()) {
        if (from.x == to.x) {
            return (from.y..to.y).map { withVerticalPosition(it) }
        }

        if (from.y == to.y) {
            return (from.x..to.x).map { withHorizontalPosition(it) }
        }

        // calculate diagonal line
        val dx = to.x - from.x
        val dy = to.y - from.y
        val direction = dy.compareTo(0)
        return (0..dx).map { delta ->
            Point(from.x + delta, from.y + direction * delta)
        }
    }
}

data class Point(val x: Int, val y: Int)