package cinema

fun main() {
    val rows = input("Enter the number of rows:")
    val seats = input("Enter the number of seats in each row:")

    val cinema = Cinema(rows, seats)

    do {
        val choice = input("\n" +
                "1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit")

        when (choice) {
            1 -> showTheSeats(cinema)
            2 -> buyATicket(cinema)
            3 -> printStatistics(cinema)
        }
    } while (choice != 0)

}

fun input(s: String): Int {
    println(s)
    return readLine()!!.toInt()
}

fun showTheSeats(cinema: Cinema) {
    println()
    println("Cinema:")
    print(" ")
    repeat(cinema.seats) {
        print(" ${it + 1}")
    }
    println()

    repeat(cinema.rows) { rowIndex: Int ->
        print(rowIndex + 1)
        repeat(cinema.seats) { seatIndex: Int ->
            print(" ${cinema.field[rowIndex][seatIndex]}")
        }
        println()
    }
    println()
}

fun buyATicket(cinema: Cinema) {
    var row: Int
    var seat: Int
    do {
        do {
            println()
            row = input("Enter a row number:")
            seat = input("Enter a seat number in that row:")
        } while (!cinema.correctInput(row, seat))
    } while (cinema.occupied(row, seat))

    cinema.field[row - 1][seat - 1] = 'B'

    println()
    println("Ticket price: $${cinema.price(row)}")
}

fun printStatistics(cinema: Cinema) {
    println()
    println("Number of purchased tickets: ${cinema.purchased()}")
    println("Percentage: ${cinema.percentage().format(2)}%")
    println("Current income: $${cinema.currentIncome()}")
    println("Total income: $${cinema.totalIncome()}")
}

class Cinema(val rows: Int, val seats: Int) {
    val field = List(rows) { MutableList(seats) { 'S' } }

    fun purchased(): Int {
        var count = 0
        repeat(rows) { rowIndex: Int ->
            repeat(seats) { seatIndex: Int ->
                if (field[rowIndex][seatIndex] == 'B') count++
            }
        }
        return count
    }

    fun percentage(): Double {
        return purchased().toDouble() / (rows * seats) * 100
    }

    fun price(row: Int): Int {
        return if (rows * seats > 60 && row > rows / 2) {
            8
        } else {
            10
        }
    }

    fun currentIncome(): Int {
        var income = 0
        repeat(rows) { rowIndex: Int ->
            repeat(seats) { seatIndex: Int ->
                if (field[rowIndex][seatIndex] == 'B') income += price(rowIndex + 1)
            }
        }
        return income
    }

    fun totalIncome(): Int {
        var income = 0
        repeat(rows) { rowIndex: Int ->
            income += price(rowIndex + 1) * seats
        }
        return income
    }

    fun correctInput(row: Int, seat: Int): Boolean {
        return if (row in 1..rows && seat in 1..seats) {
            true
        } else {
            println("Wrong input!")
            false
        }
    }

    fun occupied(row: Int, seat: Int): Boolean {
        return if (field[row - 1][seat - 1] == 'B') {
            println()
            println("That ticket has already been purchased!")
            true
        } else {
            false
        }
    }
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)