import java.io.File

/**
 * Reads lines from the given input txt file and parses each element to integer.
 */
fun readIntegerFileInput(name: String): List<Int> {
    return readLines(name).map {
        it.toInt()
    }
}

fun readLines(name: String): List<String> {
    return File("src", "$name.txt").readLines()
}

/**
 * Asserts certain state
 */
fun assert(value: Boolean, message: String= "Invalid assertion") {
    if (!value) {
        throw AssertionError(message)
    }
}