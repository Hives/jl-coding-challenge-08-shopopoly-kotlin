import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GameLedgerTest {
    val board = Board(emptyList()) // should mock this!
    val player1 = Player("Paul", board)
    val player2 = Player("Yvonne", board)
    val oxfordStreet = Location.Retail(
        name = "Oxford Street",
        cost = GBP(1000),
        group = Group.BLUE,
        undeveloped = DevelopmentType.RentOnly(GBP(10)),
        ministore = DevelopmentType.CostAndRent(GBP(200), GBP(20)),
        supermarket = DevelopmentType.CostAndRent(GBP(300), GBP(30)),
        megastore = DevelopmentType.CostAndRent(GBP(400), GBP(40))
    )

    @Test
    fun `bank pays bonus to player`() {
        GameLedger.payPlayerBonus(player1, GBP(123))
        val transaction = GameLedger.history.last()
        assertEquals(Bank, transaction.payer)
        assertEquals(player1, transaction.receiver)
        assertEquals(GBP(123), transaction.amount)
    }

    @Test
    fun `player pays rent on an undeveloped retail location`() {
        GameLedger.payRent(player1, player2, GBP(20), oxfordStreet)

        val transaction = GameLedger.history.last() as GameLedger.Transaction.Rent
        assertEquals(player1, transaction.payer)
        assertEquals(player2, transaction.receiver)
        assertEquals(GBP(20), transaction.amount)
        assertEquals(oxfordStreet, transaction.location)
    }

    @Test
    fun `player purchases a retail location`() {
        GameLedger.purchaseLocation(player1, oxfordStreet)
        val transaction = GameLedger.history.last() as GameLedger.Transaction.Purchase
        assertEquals(player1, transaction.payer)
        assertEquals(Bank, transaction.receiver)
        assertEquals(GBP(1000), transaction.amount)
        assertEquals(oxfordStreet, transaction.location)
    }

    @Test
    fun `player develops a location`() {
        GameLedger.developLocation(player1, oxfordStreet, DevelopmentLevel.SUPERMARKET)
        val transaction = GameLedger.history.last() as GameLedger.Transaction.Development
        assertEquals(player1, transaction.payer)
        assertEquals(Bank, transaction.receiver)
        assertEquals(GBP(300), transaction.amount)
        assertEquals(oxfordStreet, transaction.location)
        assertEquals(DevelopmentLevel.SUPERMARKET, transaction.developmentLevel)
    }

    @Test
    fun `player's balance is 0 in a new game`() {
        assertEquals(GameLedger.getBalance(player1), Credit(GBP(0)))
    }
    @Test
    fun `player's balance reflects bonus payment`() {
        GameLedger.payPlayerBonus(player1,GBP(100))
        assertEquals(GameLedger.getBalance(player1),Credit(GBP(100)))
    }

    @Test
    fun `player's balance reflects bonus credit and rent debit payments`() {
        GameLedger.payPlayerBonus(player1, GBP(100))
        GameLedger.payRent(player1, player2, GBP(50), oxfordStreet)
        assertThat(GameLedger.getBalance(player1)).isEqualTo(Credit(GBP(50)))
    }

    @Test
    fun `player's balance reflects all varieties of transactions`() {
        GameLedger.payPlayerBonus(player1, GBP(1500))
        GameLedger.payRent(player1, player2, GBP(10), oxfordStreet)
        GameLedger.payRent(player2, player1, GBP(20), oxfordStreet)
        GameLedger.purchaseLocation(player1, oxfordStreet)
        GameLedger.developLocation(player1, oxfordStreet, DevelopmentLevel.MINISTORE)

        assertEquals(GameLedger.getBalance(player1), Credit(GBP(310)))
    }

    @Test
    fun `handles negative player balances`() {
        GameLedger.payPlayerBonus(player1 , GBP(10))
        GameLedger.payRent(player1, player2, GBP(20), oxfordStreet)

        assertEquals(GameLedger.getBalance(player1), Debit(GBP(10)))
    }
}
