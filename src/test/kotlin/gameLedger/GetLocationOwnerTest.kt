package gameLedger

import Board
import DevelopmentType
import GBP
import GameLedger
import Group
import Location
import Player
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetLocationOwnerTest {
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
    fun `returns no owner for unpurchased location`() {
        assertThat(GameLedger.getLocationOwner(oxfordStreet)).isNull()
    }

    @Test
    fun `returns owner for purchased location`() {
        GameLedger.purchaseLocation(player1, oxfordStreet)
        assertThat(GameLedger.getLocationOwner(oxfordStreet)).isEqualTo(player1)
    }

    @Test
    fun `returns owner for re-purchased location`() {
        GameLedger.purchaseLocation(player1, oxfordStreet)
        GameLedger.purchaseLocation(player2, oxfordStreet)
        assertThat(GameLedger.getLocationOwner(oxfordStreet)).isEqualTo(player2)
    }

    @Test
    fun `doesn't return the owner of a different location`() {
        GameLedger.purchaseLocation(player1, oxfordStreet)
        GameLedger.purchaseLocation(player2, factory)
        assertThat(GameLedger.getLocationOwner(oxfordStreet)).isEqualTo(player1)
    }

}
