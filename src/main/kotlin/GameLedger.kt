object GameLedger {
    val history = mutableListOf<Transaction>()

    fun payPlayerBonus(player: Player, amount: GBP) {
        history.add(
            Transaction.Bonus(
                receiver = player,
                amount = amount
            )
        )
    }

    fun payRent(payer: Player, receiver: Player, amount: GBP, location: Location.Rentable) {
        history.add(
            Transaction.Rent(
                payer = payer,
                receiver = receiver,
                amount = amount,
                location = location
            )
        )
    }

    fun purchaseLocation(player: Player, location: Location.Purchasable) {
        history.add(
            Transaction.Purchase(
                player = player,
                location = location
            )
        )
    }

    fun developLocation(player: Player, amount: GBP, location: Location.Retail, level: DevelopmentLevel) {
        history.add(
            Transaction.Development(
                player = player,
                amount = amount,
                location = location,
                developmentLevel = level
            )
        )
    }

    sealed class Transaction(
        val payer: Role,
        val receiver: Role,
        val amount: GBP
    ) {
        class Bonus(receiver: Player, amount: GBP) :
            Transaction(Bank, receiver, amount)

        class Rent(payer: Player, receiver: Player, amount: GBP, val location: Location.Rentable) :
            Transaction(payer, receiver, amount)

        class Purchase(player: Player, val location: Location.Purchasable) :
            Transaction(player, Bank, location.cost)

        class Development(player: Player, amount: GBP, val location: Location, val developmentLevel: DevelopmentLevel) :
            Transaction(player, Bank, amount)
    }
}
