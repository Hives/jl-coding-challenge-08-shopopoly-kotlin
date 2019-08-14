sealed class DevelopmentType {
    data class RentOnly(val rent: GBP) : DevelopmentType()
    data class CostAndRent(val cost: GBP, val rent: GBP) : DevelopmentType()
}
