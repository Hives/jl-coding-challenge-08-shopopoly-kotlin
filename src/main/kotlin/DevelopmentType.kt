sealed class DevelopmentType {
    data class RentOnly(val rent: Int)
    data class CostAndRent(val cost: Int, val rent: Int)
}
