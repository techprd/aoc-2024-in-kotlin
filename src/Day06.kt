fun main() {

    fun part1(input: List<String>): Int {
        return runGame(getMap(input)).size
    }

    fun part2(input: List<String>): Int {
        return runGame(getMap(input)).count { pos ->
            runGame(getMap(input), pos).isNotEmpty()
        }
    }

    // Read the input from the `src/Day06.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

val directions = mapOf(
    "^" to Position(0, -1, ">"),
    "v" to Position(0, 1, "<"),
    "<" to Position(-1, 0, "^"),
    ">" to Position(1, 0, "v")
)

data class Position(var x: Int, var y: Int, var direction: String)
data class Map(val map: MutableList<MutableList<Char>>, var current: Position) {
    fun resetCurrent() {
        map[current.y][current.x] = '.'
    }
}

fun runGame(input: Map, obstacle: Pair<Int, Int>? = null): Set<Pair<Int, Int>> {
    var (map, current) = input
    val visited = mutableSetOf<Pair<Int, Int>>()
    val moves = mutableSetOf<Position>()

    obstacle?.let { map[it.second][it.first] = '#' }
    input.resetCurrent()

    while (map.getOrNull(current.y)?.getOrNull(current.x) != null) {
        val next = current.copy()

        while (map.getOrNull(next.y)?.getOrNull(next.x) == '.') {
            if (obstacle == null) visited.add(next.x to next.y)
            val currentStep = directions[current.direction]!!
            next.x += currentStep.x
            next.y += currentStep.y
        }

        if (map.getOrNull(next.y)?.getOrNull(next.x) == '#') {
            val currentStep = directions[current.direction]!!
            next.x -= currentStep.x
            next.y -= currentStep.y
            next.direction = currentStep.direction

            if (moves.contains(next)) return moves.map { it.x to it.y }.toSet()
            moves.add(next)
        }

        current = next
    }

    return if (obstacle == null) visited else emptySet()
}

fun getMap(input: List<String>): Map {
    val cells = input.map { it.toMutableList() }.toMutableList()
    val startY = cells.indexOfFirst { it.contains('^') }
    val startX = cells[startY].indexOf('^')
    val current = Position(startX, startY, "^")
    return Map(cells, current)
}


