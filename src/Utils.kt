import java.io.File


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