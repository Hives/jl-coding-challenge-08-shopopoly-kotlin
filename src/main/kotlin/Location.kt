sealed class Location {
    object FreeParking : Location()

    object Go : Location() {
        val bonus = GBP(100)
    }

    class FactoryOrWarehouse(val name: String) : Location(), Purchasable, Rentable {
        override val cost = GBP(100)
        val rent = GBP(20)
    }

    class Retail(
        val name: String,
        override val cost: GBP,
        val group: Group,
        val undeveloped: DevelopmentType.RentOnly,
        val ministore: DevelopmentType.CostAndRent,
        val supermarket: DevelopmentType.CostAndRent,
        val megastore: DevelopmentType.CostAndRent
    ) : Location(), Purchasable, Rentable

    interface Purchasable {
        val cost: GBP
    }

    interface Rentable

}
