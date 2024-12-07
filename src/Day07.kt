fun main() {
    fun part1(input: List<String>): Long {
        val parsedInput = parseInput(input)
        return parsedInput.sumOf { eq ->
            solve(eq.first, eq.second, false)
        }
    }

    fun part2(input: List<String>): Long {
        val parsedInput = parseInput(input)
        return parsedInput.sumOf { eq ->
            solve(eq.first, eq.second, true)
        }
    }

    // Read the input from the `src/Day07.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}


fun parseInput(input: List<String>): List<Pair<Long, List<Long>>> {
    return input.map { line ->
        val head = line.split(":")[0]
        val tail = line.split(":")[1]
        Pair(head.toLong(), tail.trim().split(" ").map { it.toLong() })
    }
}

fun canMake(goal: Long, acc: Long, nums: List<Long>, useConcat: Boolean): Boolean {
    if (nums.isEmpty()) {
        return goal == acc
    }
    val withAdd = acc + nums[0]
    val withProd = acc * nums[0]
    if (useConcat) {
        val withConcat = acc.concat(nums[0])
        return canMake(goal, withAdd, nums.drop(1), useConcat) ||
                canMake(goal, withProd, nums.drop(1), useConcat) ||
                canMake(goal, withConcat, nums.drop(1), useConcat)
    }
    return canMake(goal, withAdd, nums.drop(1), false) || canMake(goal, withProd, nums.drop(1), false)
}

fun solve(goal: Long, eqs: List<Long>, useConcat: Boolean): Long {
    var count = 0L
    if (canMake(goal, 0, eqs, useConcat)) {
        count += goal
    }
    return count
}

fun Long.concat(rhs: Long): Long {
    return this.toString().plus(rhs.toString()).toLong()
}