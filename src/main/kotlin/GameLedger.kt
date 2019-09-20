import arrow.core.Either

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

    fun developLocation(player: Player, location: Location.Retail, level: DevelopmentLevel) {
        history.add(
            Transaction.Development(
                player = player,
                amount = location.developmentCost(level),
                location = location,
                developmentLevel = level
            )
        )
    }

    fun getBalance(player: Player): Balance {
        val sumOfTransactions = history.filter { it.receiver == player }.map { it.amount.value }.sum() -
                history.filter { it.payer == player }.map { it.amount.value }.sum()
        return if (sumOfTransactions >= 0) {
            Credit(GBP(sumOfTransactions))
        } else {
            Debit(GBP(sumOfTransactions))
        }
    }

    fun getLocationsAndBuildings(player: Player): List<LocationStatus> {
        val locations = history
            .filter { it is Transaction.Purchase }
            .filter { it.payer == player }

        if (locations.size == 0) return emptyList()

        val location = (locations.first() as Transaction.Purchase).location

        val e = if(location is Location.Retail) {
            Either.left(Pair(location, DevelopmentLevel.MEGASTORE))
        } else {
            Either.right(location as Location.FactoryOrWarehouse)
        }

        return listOf(e)
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

typealias LocationStatus = Either<Pair<Location.Retail, DevelopmentLevel>, Location.FactoryOrWarehouse>
