object GameLedger {
    val history = mutableListOf<Transaction>()

    fun payPlayerBonus(player: Player, amount: GBP) {
        history.add(Transaction(
            payer = Bank,
            receiver = player,
            amount = amount
        ))
    }

    fun payRent(payer: Player, receiver: Player, amount: GBP, location: Location) {
        history.add(Transaction(
            payer = payer,
            receiver = receiver,
            amount = amount,
            location = location
        ))
    }

    class Transaction(
        open val payer: Role,
        open val receiver: Role,
        open val amount: GBP,
        open val location: Location? = null // : Purchaseable or : Rentable?
    )
}
