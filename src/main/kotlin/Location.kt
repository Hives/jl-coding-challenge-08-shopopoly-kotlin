sealed class Location {
    object FreeParking : Location()

    class Go : Location() {
        val bonus = 100
    }

    class FactoryOrWarehouse(val name: String) : Location() {
        val cost = 100
        val rent = 20
    }

    class Retail(
        val name: String,
        val cost: Int,
        val group: Group,
        val undeveloped: DevelopmentType.RentOnly,
        val ministore: DevelopmentType.CostAndRent,
        val supermarket: DevelopmentType.CostAndRent,
        val megastore: DevelopmentType.CostAndRent
    ) : Location()
}
