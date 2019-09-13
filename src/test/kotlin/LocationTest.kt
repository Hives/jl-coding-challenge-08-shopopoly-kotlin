import org.junit.jupiter.api.Assertions.assertEquals
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
        assertEquals(go.bonus, GBP(100))
    }

    @Test
    fun `can create FactoryOrWarehouse location`() {
        val factory = Location.FactoryOrWarehouse("My factory")

        assertTrue(factory is Location)
        assertTrue(factory is Location.FactoryOrWarehouse)
        assertEquals(GBP(100), factory.cost)
        assertEquals(GBP(20), factory.rent)
        assertEquals("My factory", factory.name)
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
        assertEquals("My retail location", retail.name)
        assertEquals(GBP(300), retail.cost)
        assertEquals(Group.BLUE, retail.group)
        assertEquals(GBP(10), retail.undeveloped.rent)
        assertEquals(GBP(20), retail.ministore.rent)
        assertEquals(GBP(200), retail.ministore.cost)
        assertEquals(GBP(30), retail.supermarket.rent)
        assertEquals(GBP(300), retail.supermarket.cost)
        assertEquals(GBP(40), retail.megastore.rent)
        assertEquals(GBP(400), retail.megastore.cost)
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
        assertEquals(retail.developmentCost(DevelopmentLevel.MINISTORE), GBP(200))
    }
}
