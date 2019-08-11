sealed class Location {
    object FreeParking : Location()

    class Go : Location() {
        val bonus = 100
    }

    class FactoryOrWarehouse(val name: String) : Location() {
        val cost = 100
        val rent = 20

        companion object {
            val maxInstances = 4
            var instances = 0
        }

        init {
            if (instances == maxInstances) {
                throw Exception("Couldn't create location. Maximum of $maxInstances factory or warehouse sites allowed.")
            }
            instances++
        }
    }

    class Retail(
        val name: String,
        val cost: Int,
        val group: Group,
        val undeveloped: DevelopmentType.RentOnly,
        val ministore: DevelopmentType.CostAndRent,
        val supermarket: DevelopmentType.CostAndRent,
        val megastore: DevelopmentType.CostAndRent
    ) : Location() {
        companion object {
            val maxInstances = 20
            var instances = 0
        }

        init {
            if (instances == maxInstances) {
                throw Exception("Couldn't create location. Maximum of $maxInstances retail sites allowed.")
            }
            instances++
        }
    }
}

sealed class DevelopmentType {
    data class RentOnly(val rent: Int)
    data class CostAndRent(val cost: Int, val rent: Int)
}

enum class Group {
    RED, GREEN, BLUE, YELLOW
}
