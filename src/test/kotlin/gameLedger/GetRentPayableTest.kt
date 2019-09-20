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

class GetRentPayableTest {
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
    val factory = Location.FactoryOrWarehouse("My factory")

    @BeforeEach
    fun `reset game ledger`() {
        GameLedger.initialise()
    }

    @Test
    fun `should return the right value for an owned factory`(){
        GameLedger.purchaseLocation(player1, factory)
        assertThat(GameLedger.getRentPayable(factory)).isEqualTo(GBP(20))
    }

    @Test
    fun `should return the right value for an owned, undeveloped retail Location`(){
        GameLedger.purchaseLocation(player1, oxfordStreet)
        assertThat(GameLedger.getRentPayable(oxfordStreet)).isEqualTo(GBP(10))
    }

    @Test
    fun `should return the right value for an owned, developed retail Location`(){
        GameLedger.purchaseLocation(player1, oxfordStreet)
        GameLedger.developLocation(player1, oxfordStreet, DevelopmentLevel.SUPERMARKET)
        assertThat(GameLedger.getRentPayable(oxfordStreet)).isEqualTo(GBP(30))
    }
}
