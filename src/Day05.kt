fun main() {

    fun part1(lines: List<String>): Int {
        val rules = lines.takeWhile { it.isNotEmpty() }
        val updates = lines.takeLastWhile { it.isNotEmpty() }
        val updateGroups = groupUpdates(updates, rules).filter { it.correctlyOrdered() }
        return updateGroups.sumOf { it.getMiddlePage() }
    }

    fun part2(lines: List<String>): Int {
        val rules = lines.takeWhile { it.isNotEmpty() }
        val updates = lines.takeLastWhile { it.isNotEmpty() }
        val updatesGroups = groupUpdates(updates, rules).filterNot { it.correctlyOrdered() }
        return updatesGroups.orderByRules().sumOf { it.getMiddlePage() }
    }

    // Read the input from the `src/Day05.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

private fun List<Update>.orderByRules(): List<Update> {
    val ordered = mutableListOf<Update>()
    this.forEach { update ->
        val orderedPages = update.pages.toMutableList()
        var changed = true
        while (changed) {
            changed = false
            update.rules.forEach { (left, right) ->
                val leftIndex = orderedPages.indexOf(left)
                val rightIndex = orderedPages.indexOf(right)
                if (leftIndex > rightIndex) {
                    orderedPages.remove(left)
                    orderedPages.add(rightIndex, left)
                    changed = true
                }
            }
        }
        ordered.add(Update(orderedPages, update.rules))
    }
    return ordered
}

fun groupUpdates(
    updates: List<String>,
    rules: List<String>
) = updates.map { it.split(",") }.map { update ->
    val updateLine = update.map { it.toInt() }
    Update(
        updateLine,
        rules.map { rule ->
            val (left, right) = rule.split("|").map { it.toInt() }
            Pair(left, right)
        }.filter { (left, right) ->
            updateLine.contains(left) && updateLine.contains(right)
        }
    )
}

data class Update(
    val pages: List<Int>,
    val rules: List<Pair<Int, Int>>
) {
    fun getMiddlePage(): Int {
        return pages[(pages.size - 1) / 2]
    }

    fun correctlyOrdered(): Boolean {
        return rules.all { rule ->
            pages.indexOf(rule.first) < pages.indexOf(rule.second)
        }
    }
}
