fun main() {

    fun part1(st: List<String>, ignoreVisited: Boolean): Int {
        val walkTrail = WalkTrails(st)
        var count = 0
        walkTrail.rows.forEach { row ->
            row.forEach { cell ->
                if (cell.value == 0) {
                    val queue = mutableListOf(cell)
                    val visited = mutableSetOf<Pair<Int, Int>>()
                    while (queue.size > 0) {
                        val current = queue.removeAt(0)
                        if (current.value == 9) {
                            count++
                        }
                        val neighbors = walkTrail.getNeighbors(current, walkTrail)
                        neighbors.forEach { neighbor ->
                            if (neighbor.value == current.value + 1) {
                                if (ignoreVisited) {
                                    queue.add(neighbor)
                                } else if (!visited.contains(neighbor.x to neighbor.y)) {
                                    visited.add(neighbor.x to neighbor.y)
                                    queue.add(neighbor)
                                }
                            }
                        }
                    }
                }
            }
        }
        return count
    }


    fun part2(input: List<String>): Int {
        return part1(input, true
        )
    }

    // Read the input from the `src/Day10.txt` file.
    val input = readInput("Day10")
    part1(input, false).println()
    part2(input).println()
}

class WalkTrails(lines: List<String>) {

    val rows: List<List<WalkPoint>> = constructWalks(lines)

    private val width = rows[0].size
    private val height = rows.size

    fun getNeighbors(cell: WalkPoint, grid: WalkTrails): List<WalkPoint> {
        val neighbors = mutableListOf<WalkPoint>()

        grid.getRight(cell)?.let { neighbors.add(it) }
        grid.getDown(cell)?.let { neighbors.add(it) }
        grid.getLeft(cell)?.let { neighbors.add(it) }
        grid.getUp(cell)?.let { neighbors.add(it) }

        return neighbors
    }

    fun getRight(cell: WalkPoint): WalkPoint? {
        return if (cell.x + 1 < width) rows[cell.y][cell.x + 1] else null
    }

    fun getLeft(cell: WalkPoint): WalkPoint? {
        return if (cell.x - 1 >= 0) rows[cell.y][cell.x - 1] else null
    }

    fun getDown(cell: WalkPoint): WalkPoint? {
        return if (cell.y + 1 < height) rows[cell.y + 1][cell.x] else null
    }

    fun getUp(cell: WalkPoint): WalkPoint? {
        return if (cell.y - 1 >= 0) rows[cell.y - 1][cell.x] else null
    }

}

private fun constructWalks(lines: List<String>): List<List<WalkPoint>> {
    return lines.mapIndexed { y, line ->
        line.mapIndexed { x, value ->
            WalkPoint(x, y, value.toString().toInt())
        }
    }
}

data class WalkPoint(val x: Int, val y: Int, val value: Int)
