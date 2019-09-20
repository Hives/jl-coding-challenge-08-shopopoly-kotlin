package gameLedger

import Board
import Credit
import Debit
import DevelopmentLevel
import DevelopmentType
import GBP
import GameLedger
import Group
import Location
import Player
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GetBalanceTest {
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
    fun `player's balance is 0 in a new game`() {
        assertEquals(GameLedger.getBalance(player1), Credit(GBP(0)))
    }
    @Test
    fun `player's balance reflects bonus payment`() {
        GameLedger.payPlayerBonus(player1, GBP(100))
        assertEquals(GameLedger.getBalance(player1), Credit(GBP(100)))
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
        GameLedger.payPlayerBonus(player1, GBP(10))
        GameLedger.payRent(player1, player2, GBP(20), oxfordStreet)

        assertEquals(GameLedger.getBalance(player1), Debit(GBP(10)))
    }
}
