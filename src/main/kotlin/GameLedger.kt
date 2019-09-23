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

    // lots of duplication in these functions, retrieving transactions. could make some sort of general method for
    // retrieving transactions from the game ledger to simplify these methods? (mike neilens' idea)
    fun getPlayersLocations(player: Player): List<Location.Purchasable> = history
        .filterIsInstance<Transaction.Purchase>()
        .reversed()
        .distinctBy { it.location }
        .filter { it.payer == player }
        .map { it.location }

    fun initialise() {
        history.clear()
    }

    fun getDevelopmentLevel(location: Location.Retail): DevelopmentLevel {
        val developments = history
            .filterIsInstance<Transaction.Development>()
            .filter { it.location == location }

        return if (developments.isEmpty()) { // can use .lastOrNull() to simplify this
            DevelopmentLevel.UNDEVELOPED
        } else {
            developments.last().developmentLevel
        }
    }

    fun getLocationOwner(location: Location.Purchasable): Role? {
        val owners = history.filterIsInstance<Transaction.Purchase>()
            .filter { it.location == location }
        return if (owners.isEmpty()) {
            null
        } else {
            owners.last().payer
        }
    }

    fun getRentPayable(location: Location.Rentable):GBP {
        if(location is Location.FactoryOrWarehouse) {
            return location.rent
        }
        if(location is Location.Retail) {
            return when(getDevelopmentLevel(location)) {
                DevelopmentLevel.UNDEVELOPED -> location.undeveloped.rent
                DevelopmentLevel.SUPERMARKET -> location.supermarket.rent
                else -> GBP(0)
            }
            return GBP(10)
        }

        return GBP(999)
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
