fun main() {

    fun part1(lines: List<String>): Int {
        return lines.sumOf { line ->
            sumMultiplications(line)
        }
    }

    fun part2(lines: List<String>): Int {
        return sumMultiplications(
            lines.joinToString { it }.split("do()").map {
                it.split("don't()").first()
            }.joinToString { it }
        )
    }

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

fun sumMultiplications(line: String): Int {
    val regex = Regex("mul\\(\\d+,\\d+\\)")
    val matches = regex.findAll(line)
    return matches.sumOf { match ->
        val (x, y) = match.value.drop(4).dropLast(1).split(",").map { it.toInt() }
        x * y
    }
}
