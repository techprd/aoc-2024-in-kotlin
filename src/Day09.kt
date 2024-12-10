fun main() {

    fun part1(st: String, part2: Boolean): Long {
        val disks = parse(st)

        var i = disks.size - 1
        while (i >= 0) {
            if (disks[i].id == -1) {
                i--
                continue
            }
            val empty = if (part2) {
                disks.indexOfFirst { it.id == -1 && it.count >= disks[i].count }
            } else {
                disks.indexOfFirst { it.id == -1 }
            }
            if (empty != -1 && empty < i) {
                i = move(disks, i, empty)
            }
            i--
        }
        return checksum(disks)
    }

    fun part2(input: String): Long {
        return part1(input, true)
    }

    // Read the input from the `src/Day09.txt` file.
    val input = readLine("Day09")
    part1(input, false).println()
    part2(input).println()
}

fun split(disks: MutableList<Disk>, i: Int, count: Int) {
    val add = mutableListOf(
        Disk(disks[i].id, count),
        Disk(disks[i].id, disks[i].count - count)
    )
    disks.splice(i, 1, add)
}

fun MutableList<Disk>.splice(index: Int, deleteCount: Int, elements: MutableList<Disk>) {
    // Remove elements
    repeat(deleteCount) {
        if (index < this.size) {
            removeAt(index)
        }
    }
    // Insert new elements
    addAll(index, elements.toList())
}

fun move(disks: MutableList<Disk>, from: Int, to: Int): Int {
    var from = from
    if (disks[to].count > disks[from].count) {
        split(disks, to, disks[from].count);
        from++
    }
    if (disks[from].count > disks[to].count) {
        split(disks, from, disks[from].count - disks[to].count)
        from++
    }
    val tmp = disks[to].id
    disks[to].id = disks[from].id
    disks[from].id = tmp
    return from
}

fun parse(input: String): MutableList<Disk> {
    val disks = mutableListOf<Disk>()
    for (i in input.indices) {
        if (i % 2 == 0) disks.add(Disk(i / 2, "${input[i]}".toInt()))
        else disks.add(Disk(-1, "${input[i]}".toInt()))
    }
    return disks
}

fun checksum(disks: List<Disk>): Long {
    var sum = 0L
    var index = 0
    for (i in disks.indices) {
        for (j in 0 until disks[i].count) {
            if (disks[i].id != -1) sum += disks[i].id * index
            index++
        }
    }
    return sum
}

data class Disk(var id: Int, var count: Int)