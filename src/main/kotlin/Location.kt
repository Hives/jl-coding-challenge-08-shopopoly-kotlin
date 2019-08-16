sealed class Location {
    object FreeParking : Location()

    object Go : Location() {
        val bonus = GBP(100)
    }

    class FactoryOrWarehouse(val name: String) : Purchasable(GBP(100)), Rentable {
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
    ) : Purchasable(cost), Rentable

    open class Purchasable(open val cost: GBP) : Location()

    interface Rentable

}
