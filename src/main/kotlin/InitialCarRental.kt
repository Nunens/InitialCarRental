class Car(title: String, priceCode: Int) {
    companion object {
        const val MUSCLE = 2
        const val ECONOMY = 0
        const val SUPERCAR = 1
    }

    private val _title: String = title
    private var _priceCode: Int = priceCode

    fun getPriceCode(): Int {
        return _priceCode
    }

    fun getTitle(): String {
        return _title
    }
}

class Rental(car: Car, daysRented: Int) {
    private val _car: Car = car
    private val _daysRented: Int = daysRented

    fun getDaysRented(): Int {
        return _daysRented
    }

    fun getCar(): Car {
        return _car
    }
}

class Customer(name: String) {
    private val _name: String = name
    private var _rentals = ArrayList<Rental>()

    fun addRental(arg: Rental) {
        _rentals.add(arg)
    }

    private fun getName(): String {
        return _name
    }

    fun billingStatement(): String {
        var totalAmount = 0.0
        var frequentRenterPoints = 0
        val builder = StringBuilder()
        builder.append("Rental Record for ")
        builder.appendLine(getName())
        //builder.append("\n")
        for (each in _rentals) {
            // add frequent renter points
            frequentRenterPoints += 1
            if (each.getDaysRented() >= 2) {
                // add bonus for a two day new release rental
                frequentRenterPoints += 1
            }
            try {
                var thisAmount = 0.0
                when (each.getCar().getPriceCode()) {
                    Car.MUSCLE -> {
                        thisAmount += 200
                        if (each.getDaysRented() > 3) {
                            thisAmount += ((each.getDaysRented()).toDouble() - 3) * 50.0
                        }
                    }

                    Car.SUPERCAR -> {
                        thisAmount += (each.getDaysRented()).toDouble() * 200.0
                    }

                    Car.ECONOMY -> {
                        thisAmount += 80
                        if (each.getDaysRented() > 2) {
                            thisAmount += ((each.getDaysRented()) - 2).toDouble() * 30.0
                        }
                    }
                }
                //show figures for this rental
                builder.append("\t")
                builder.append(each.getCar().getTitle())
                builder.append("\t")
                builder.appendLine(thisAmount.toString())
                totalAmount += thisAmount
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //add footer lines
        builder.appendLine("Final rental payment owed $totalAmount")
        builder.append(buildString {
            append("You received an additional ")
            append(frequentRenterPoints)
            append(" frequent customer points")
        })
        return builder.toString()
    }
}

private val rental1 = Rental(Car("Mustang", Car.MUSCLE), 5)
private val rental2 = Rental(Car("Lambo", Car.SUPERCAR), 2)
private val rental3 = Rental(Car("Tazz", Car.ECONOMY), 3)
private val customer = Customer("Stephen")

fun main() {
    customer.addRental(rental1)
    customer.addRental(rental2)
    customer.addRental(rental3)
    println(customer.billingStatement())
}