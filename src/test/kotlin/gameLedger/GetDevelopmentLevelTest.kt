package gameLedger

import Board
import DevelopmentLevel
import DevelopmentType
import GBP
import GameLedger
import Group
import Location
import Player
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetDevelopmentLevelTest {
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
    fun `gets the development level of an owned, undeveloped site`() {
        GameLedger.purchaseLocation(player1, oxfordStreet)
        assertThat(GameLedger.getDevelopmentLevel(oxfordStreet)).isEqualTo(DevelopmentLevel.UNDEVELOPED)
    }

    @Test
    fun `gets the development level of a developed site`() {
        GameLedger.purchaseLocation(player1, oxfordStreet)
        GameLedger.developLocation(player1,oxfordStreet,DevelopmentLevel.MINISTORE)
        assertThat(GameLedger.getDevelopmentLevel(oxfordStreet)).isEqualTo(DevelopmentLevel.MINISTORE)
    }

    @Test
    fun `gets the most recent development level of a site`() {
        GameLedger.purchaseLocation(player1, oxfordStreet)
        GameLedger.developLocation(player1,oxfordStreet,DevelopmentLevel.MINISTORE)
        GameLedger.developLocation(player1,oxfordStreet,DevelopmentLevel.MEGASTORE)
        assertThat(GameLedger.getDevelopmentLevel(oxfordStreet)).isEqualTo(DevelopmentLevel.MEGASTORE)
    }
}
