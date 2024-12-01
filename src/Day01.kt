fun main() {
    fun part1(input: List<String>): Int {
        val leftList = getLeft(input)
        val rightList = getRight(input)

        return leftList.zip(rightList).sumOf {
            if (it.first - it.second > 0) {
                it.first - it.second
            } else {
                it.second - it.first
            }
        }
    }

    fun part2(input: List<String>): Int {
        val leftList = getLeft(input)
        val rightList = getRight(input)

        return leftList.sumOf { left ->
            var similarity = 0
            rightList.forEach { right ->
                if (left == right) {
                    similarity++
                }
            }
            left * similarity
        }
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private fun getRight(lines: List<String>): List<Int> {
    val rightList = lines.map {
        // last 5 characters
        val lastFive = it.substring(8, 13)
        lastFive.toInt()
    }.sorted()
    return rightList
}

private fun getLeft(lines: List<String>): List<Int> {
    val leftList = lines.map {
        // first 5 characters
        val firstFive = it.substring(0, 5)
        firstFive.toInt()
    }.sorted()
    return leftList
}
