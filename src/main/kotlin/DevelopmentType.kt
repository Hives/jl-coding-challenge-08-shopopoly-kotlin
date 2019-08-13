sealed class DevelopmentType {
    data class RentOnly(val rent: Int) : DevelopmentType()
    data class CostAndRent(val cost: Int, val rent: Int) : DevelopmentType()
}
