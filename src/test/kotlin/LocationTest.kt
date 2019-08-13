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
        val go = Location.Go()

        assertTrue(go is Location)
        assertTrue(go is Location.Go)
        assertEquals(go.bonus, 100)
    }

    @Test
    fun `can create FactoryOrWarehouse location`() {
        val factory = Location.FactoryOrWarehouse("My factory")

        assertTrue(factory is Location)
        assertTrue(factory is Location.FactoryOrWarehouse)
        assertEquals(100, factory.cost)
        assertEquals(20, factory.rent)
        assertEquals("My factory", factory.name)
    }

        @Test
        fun `can create Retail location`() {
            val retail = Location.Retail(
                name = "My retail location",
                cost = 300,
                group = Group.BLUE,
                undeveloped = DevelopmentType.RentOnly(10),
                ministore = DevelopmentType.CostAndRent(200, 20),
                supermarket = DevelopmentType.CostAndRent(300, 30),
                megastore = DevelopmentType.CostAndRent(400, 40)
            )
            assertTrue(retail is Location)
            assertTrue(retail is Location.Retail)
            assertEquals("My retail location", retail.name)
            assertEquals(300, retail.cost)
            assertEquals(Group.BLUE, retail.group)
            assertEquals(10, retail.undeveloped.rent)
            assertEquals(20, retail.ministore.rent)
            assertEquals(200, retail.ministore.cost)
            assertEquals(30, retail.supermarket.rent)
            assertEquals(300, retail.supermarket.cost)
            assertEquals(40, retail.megastore.rent)
            assertEquals(400, retail.megastore.cost)
        }

}
