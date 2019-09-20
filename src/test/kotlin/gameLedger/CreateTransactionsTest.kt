package gameLedger

import Bank
import Board
import DevelopmentLevel
import DevelopmentType
import GBP
import GameLedger
import Group
import Location
import Player
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CreateTransactionsTest {
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

    @BeforeEach
    fun `reset game ledger`() {
        GameLedger.initialise()
    }


    @Test
    fun `bank pays bonus to player`() {
        GameLedger.payPlayerBonus(player1, GBP(123))
        val transaction = GameLedger.history.last()
        assertThat(Bank).isEqualTo(transaction.payer)
        assertThat(player1).isEqualTo(transaction.receiver)
        assertThat(GBP(123)).isEqualTo(transaction.amount)
    }

    @Test
    fun `initialise clears transactions`(){
        GameLedger.payPlayerBonus(player1, GBP(123))
        GameLedger.initialise()
        assertThat(GameLedger.history).isEmpty()
    }

    @Test
    fun `player pays rent on an undeveloped retail location`() {
        GameLedger.payRent(player1, player2, GBP(20), oxfordStreet)

        val transaction = GameLedger.history.last() as GameLedger.Transaction.Rent
        assertThat(player1).isEqualTo(transaction.payer)
        assertThat(player2).isEqualTo(transaction.receiver)
        assertThat(GBP(20)).isEqualTo(transaction.amount)
        assertThat(oxfordStreet).isEqualTo(transaction.location)
    }

    @Test
    fun `player purchases a retail location`() {
        GameLedger.purchaseLocation(player1, oxfordStreet)
        val transaction = GameLedger.history.last() as GameLedger.Transaction.Purchase
        assertThat(player1).isEqualTo(transaction.payer)
        assertThat(Bank).isEqualTo(transaction.receiver)
        assertThat(GBP(1000)).isEqualTo(transaction.amount)
        assertThat(oxfordStreet).isEqualTo(transaction.location)
    }

    @Test
    fun `player develops a location`() {
        GameLedger.developLocation(player1, oxfordStreet, DevelopmentLevel.SUPERMARKET)
        val transaction = GameLedger.history.last() as GameLedger.Transaction.Development
        assertThat(player1).isEqualTo(transaction.payer)
        assertThat(Bank).isEqualTo(transaction.receiver)
        assertThat(GBP(300)).isEqualTo(transaction.amount)
        assertThat(oxfordStreet).isEqualTo(transaction.location)
        assertThat(DevelopmentLevel.SUPERMARKET).isEqualTo(transaction.developmentLevel)
    }
}
