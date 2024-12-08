fun main() {

    fun part1(grid: List<String>): Int {
        val frequencies = grid.flatMap { it.toCharArray().toList() }.distinct().filter { it != '.' }
        val allAntinodes = frequencies.map { freq ->
            val antennas = findAntennas(grid, freq)
            val antinodes = findAntinodes(antennas)
            antinodes
        }.flatten()

        val gridWithAntinodes = constructGridWithAntinodes(grid, allAntinodes)
        return gridWithAntinodes.sumOf { row -> row.count { it == '#' } }
    }

    fun part2(input: List<String>): Int {
        val grid = input
        val frequencies = grid.flatMap { it.toCharArray().toList() }.distinct().filter { it != '.' }
        val allAntinodes = frequencies.map { freq ->
            val antennas = findAntennas(grid, freq)
            val antinodes = findAllAntinodes(antennas, grid.size)
            antinodes
        }.flatten()

        val gridWithAntinodes = constructGridWithAntinodes(grid, allAntinodes)
        gridWithAntinodes.forEach { println(it) }
        return gridWithAntinodes.sumOf { row -> row.count { it == '#' } }
    }

    // Read the input from the `src/Day08.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}

fun findAntennas(grid: List<String>, frequency: Char): List<Cell> {
    return grid.flatMapIndexed { y, row ->
        row.withIndex()
            .filter { it.value == frequency }
            .map { x -> Cell(x.index, y, x.value) }
    }
}

fun findAntinodes(antennas: List<Cell>): List<Cell> {
    return antennas.indices.flatMap { i ->
        (i + 1 until antennas.size).flatMap { j ->
            val (x1, y1) = antennas[i]
            val (x2, y2) = antennas[j]
            val dx = x2 - x1
            val dy = y2 - y1
            listOf(Cell(x1 - dx, y1 - dy, '#'), Cell(x2 + dx, y2 + dy, '#'))
        }
    }
}

fun constructGridWithAntinodes(
    grid: List<String>,
    allAntinodes: List<Cell>
) = grid.mapIndexed { y, row ->
    row.toCharArray().mapIndexed { x, c ->
        when {
            Cell(x, y, '#') in allAntinodes -> '#'
            c != '.' -> c
            else -> c
        }
    }.joinToString("")
}

fun findAllAntinodes(antennas: List<Cell>, size: Int): Set<Cell> {
    val antinodes = mutableSetOf<Cell>()

    for (i in antennas.indices) {
        for (j in (i + 1) until antennas.size) {
            val (x1, y1) = antennas[i]
            val (x2, y2) = antennas[j]

            val dx = x2 - x1
            val dy = y2 - y1

            fun extend(x: Int, y: Int, stepX: Int, stepY: Int) {
                var cx = x
                var cy = y
                while (cx in 0 until size) {
                    antinodes.add(Cell(cx, cy, '#'))
                    cx += stepX
                    cy += stepY
                }
            }

            extend(x1, y1, -dx, -dy)
            extend(x2, y2, dx, dy)
        }
    }

    return antinodes
}
