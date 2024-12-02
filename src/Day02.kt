import kotlin.math.abs

fun main() {
    fun part1(reports: List<String>): Int {
        return reports.count { line ->
            val levels = line.split(" ").map { it.toInt() }
            isSafe(levels)
        }
    }

    fun part2(reports: List<String>): Int {
        return reports.count { line ->
            val levels = line.split(" ").map { it.toInt() }
            isSafeOffByOne(levels)
        }
    }

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private fun isSafeOffByOne(levels: List<Int>): Boolean {
    val isSafe = isSafe(levels)
    if (isSafe) {
        return true
    }
    for (i in levels.indices) {
        val levelMinusI = levels.toMutableList()
        levelMinusI.removeAt(i)
        if (isSafe(levelMinusI)) {
            return true
        }
    }
    return false
}

private fun isSafe(levels: List<Int>): Boolean {
    return (isLineIncreasingAndNotDecreasing(levels) || isLineDecreasingAndNotIncreasing(levels)) && hasValidDifference(levels)
}

private fun isLineIncreasingAndNotDecreasing(levels: List<Int>): Boolean {
    for (i in 0 until levels.size - 1) {
        if (levels[i] > levels[i + 1]) {
            return false
        }
    }
    return true
}

private fun isLineDecreasingAndNotIncreasing(levels: List<Int>): Boolean {
    for (i in 0 until levels.size - 1) {
        if (levels[i] < levels[i + 1]) {
            return false
        }
    }
    return true
}

private fun hasValidDifference(levels: List<Int>): Boolean {
    levels.zipWithNext().forEach {
        if (!hasValidDistance(it.first, it.second)) {
            return false
        }
    }
    return true
}

private fun hasValidDistance(a: Int, b: Int): Boolean {
    return abs(b - a) in 1..3
}