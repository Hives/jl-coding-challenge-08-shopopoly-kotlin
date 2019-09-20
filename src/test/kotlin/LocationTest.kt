import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class LocationTest {
    @Test
    fun `can create FreeParking location`() {
        val freeParking = Location.FreeParking

        assertTrue(freeParking is Location)
        assertTrue(freeParking is Location.FreeParking)
    }

    @Test
    fun `can create Go location`() {
        val go = Location.Go

        assertTrue(go is Location)
        assertTrue(go is Location.Go)
        assertThat(go.bonus).isEqualTo(GBP(100))
    }

    @Test
    fun `can create FactoryOrWarehouse location`() {
        val factory = Location.FactoryOrWarehouse("My factory")

        assertTrue(factory is Location)
        assertTrue(factory is Location.FactoryOrWarehouse)
        assertThat(GBP(100)).isEqualTo(factory.cost)
        assertThat(GBP(20)).isEqualTo(factory.rent)
        assertThat("My factory").isEqualTo(factory.name)
    }

    @Test
    fun `can create Retail location`() {
        val retail = Location.Retail(
            name = "My retail location",
            cost = GBP(300),
            group = Group.BLUE,
            undeveloped = DevelopmentType.RentOnly(GBP(10)),
            ministore = DevelopmentType.CostAndRent(GBP(200), GBP(20)),
            supermarket = DevelopmentType.CostAndRent(GBP(300), GBP(30)),
            megastore = DevelopmentType.CostAndRent(GBP(400), GBP(40))
        )
        assertTrue(retail is Location)
        assertTrue(retail is Location.Retail)
        assertThat("My retail location").isEqualTo(retail.name)
        assertThat(GBP(300)).isEqualTo(retail.cost)
        assertThat(Group.BLUE).isEqualTo(retail.group)
        assertThat(GBP(10)).isEqualTo(retail.undeveloped.rent)
        assertThat(GBP(20)).isEqualTo(retail.ministore.rent)
        assertThat(GBP(200)).isEqualTo(retail.ministore.cost)
        assertThat(GBP(30)).isEqualTo(retail.supermarket.rent)
        assertThat(GBP(300)).isEqualTo(retail.supermarket.cost)
        assertThat(GBP(40)).isEqualTo(retail.megastore.rent)
        assertThat(GBP(400)).isEqualTo(retail.megastore.cost)
    }

    @Test
    fun `a location can return the cost of developing to a particular level`() {
        val retail = Location.Retail(
            name = "My retail location",
            cost = GBP(300),
            group = Group.BLUE,
            undeveloped = DevelopmentType.RentOnly(GBP(10)),
            ministore = DevelopmentType.CostAndRent(GBP(200), GBP(20)),
            supermarket = DevelopmentType.CostAndRent(GBP(300), GBP(30)),
            megastore = DevelopmentType.CostAndRent(GBP(400), GBP(40))
        )
        assertThat(retail.developmentCost(DevelopmentLevel.MINISTORE)).isEqualTo(GBP(200))
    }
}
