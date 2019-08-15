import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class GameLedgerTest {
    val player1 = Player("Paul")
    val player2 = Player("Yvonne")
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
    fun `pay bonus to player`() {
        GameLedger.payPlayerBonus(player1, GBP(123))
        val transaction = GameLedger.history.last()
        assertEquals(Bank, transaction.payer)
        assertEquals(player1, transaction.receiver)
        assertEquals(GBP(123), transaction.amount)
        assertNull(transaction.location)
    }

    @Test
    fun `player pays rent to another player`() {
        GameLedger.payRent(player1, player2, GBP(20), oxfordStreet)
        val transaction = GameLedger.history.last()
        assertEquals(player1, transaction.payer)
        assertEquals(player2, transaction.receiver)
        assertEquals(GBP(20), transaction.amount)
        assertEquals(oxfordStreet, transaction.location)
    }

}
