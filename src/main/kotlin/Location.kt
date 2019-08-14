sealed class Location {
    object FreeParking : Location()

    class Go : Location() {
        val bonus = GBP(100)
    }

    class FactoryOrWarehouse(val name: String) : Location() {
        val cost = GBP(100)
        val rent = GBP(20)
    }

    class Retail(
        val name: String,
        val cost: GBP,
        val group: Group,
        val undeveloped: DevelopmentType.RentOnly,
        val ministore: DevelopmentType.CostAndRent,
        val supermarket: DevelopmentType.CostAndRent,
        val megastore: DevelopmentType.CostAndRent
    ) : Location()

}
