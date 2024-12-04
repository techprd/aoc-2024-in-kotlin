fun main() {

    fun part1(lines: List<String>): Int {
        val grid = Grid(lines)
        return countXMAS(grid)
    }

    fun part2(lines: List<String>): Int {
        val grid = Grid(lines)
        return countX_MAS(grid)
    }

    // Read the input from the `src/Day04.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

fun countX_MAS(grid: Grid): Int {
    var xmasCount = 0
    grid.cells.forEach { row ->
        row.forEach { cell ->
            if (cell.value == 'A') {
                val rightUp = grid.getRightUp(cell)
                val rightDown = grid.getRightDown(cell)
                val leftUp = grid.getLeftUp(cell)
                val leftDown = grid.getLeftDown(cell)
                if (rightUp == null || rightDown == null || leftUp == null || leftDown == null) {
                    return@forEach
                }
                if (
                    (leftUp.value == 'M' && rightUp.value == 'S' && leftDown.value == 'M' && rightDown.value == 'S') ||
                    (leftUp.value == 'S' && rightUp.value == 'M' && leftDown.value == 'S' && rightDown.value == 'M') ||
                    (leftUp.value == 'M' && rightUp.value == 'M' && leftDown.value == 'S' && rightDown.value == 'S') ||
                    (leftUp.value == 'S' && rightUp.value == 'S' && leftDown.value == 'M' && rightDown.value == 'M')
                ) {
                    xmasCount++
                }
            }
        }
    }
    return xmasCount
}

fun countXMAS(grid: Grid): Int {
    var xmasCount = 0
    grid.cells.forEach { row ->
        row.forEach { cell ->
            if (cell.value == 'X') {
                val directions = listOf(
                    { cell: Cell -> grid.getRight(cell) },
                    { cell: Cell -> grid.getLeft(cell) },
                    { cell: Cell -> grid.getDown(cell) },
                    { cell: Cell -> grid.getUp(cell) },
                    { cell: Cell -> grid.getRightDown(cell) },
                    { cell: Cell -> grid.getRightUp(cell) },
                    { cell: Cell -> grid.getLeftDown(cell) },
                    { cell: Cell -> grid.getLeftUp(cell) }
                )
                directions.forEach { direction ->
                    if (checkNeighboursForXMAS(cell, direction)) {
                        xmasCount++
                    }
                }
            }
        }
    }
    return xmasCount
}

fun checkNeighboursForXMAS(xCell: Cell, getNeighbour: (c: Cell) -> Cell?): Boolean {
    val first = getNeighbour(xCell)
    if (first == null || first.value != 'M') {
        return false
    }
    val second = getNeighbour(first)
    if (second == null || second.value != 'A') {
        return false
    }
    val third = getNeighbour(second)
    return !(third == null || third.value != 'S')
}

fun constructCells(lines: List<String>): List<List<Cell>> {
    return lines.mapIndexed { y, line ->
        line.mapIndexed { x, value ->
            Cell(x, y, value)
        }
    }
}

data class Grid(val lines: List<String>) {

    val cells: List<List<Cell>> = constructCells(lines)

    private val width = cells[0].size
    private val height = cells.size

    fun getRight(cell: Cell): Cell? {
        return if (cell.x + 1 < width) cells[cell.y][cell.x + 1] else null
    }

    fun getLeft(cell: Cell): Cell? {
        return if (cell.x - 1 >= 0) cells[cell.y][cell.x - 1] else null
    }

    fun getDown(cell: Cell): Cell? {
        return if (cell.y + 1 < height) cells[cell.y + 1][cell.x] else null
    }

    fun getUp(cell: Cell): Cell? {
        return if (cell.y - 1 >= 0) cells[cell.y - 1][cell.x] else null
    }

    fun getRightDown(cell: Cell): Cell? {
        return if (cell.x + 1 < width && cell.y + 1 < height) cells[cell.y + 1][cell.x + 1] else null
    }

    fun getRightUp(cell: Cell): Cell? {
        return if (cell.x + 1 < width && cell.y - 1 >= 0) cells[cell.y - 1][cell.x + 1] else null
    }

    fun getLeftDown(cell: Cell): Cell? {
        return if (cell.x - 1 >= 0 && cell.y + 1 < height) cells[cell.y + 1][cell.x - 1] else null
    }

    fun getLeftUp(cell: Cell): Cell? {
        return if (cell.x - 1 >= 0 && cell.y - 1 >= 0) cells[cell.y - 1][cell.x - 1] else null
    }

}

data class Cell(val x: Int, val y: Int, val value: Char)