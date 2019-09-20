package gameLedger

import Board
import DevelopmentType
import GBP
import GameLedger
import Group
import Location
import LocationStatus
import Player
import arrow.core.Either
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class GetLocationsAndBuildingsTest {
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
    val factory = Location.FactoryOrWarehouse("OriginPark")

    @Test
    fun `returns emptyList when no purchases have been made`() {
        val actual = GameLedger.getLocationsAndBuildings(player1)
        assertThat(actual).isEqualTo(emptyList<LocationStatus>())
    }

    @Test
    fun `returns factory purchased by player`() {
        GameLedger.purchaseLocation(player1, factory)
        assertThat(GameLedger.getLocationsAndBuildings(player1)).isEqualTo(listOf(Either.right(factory)))
    }
}
