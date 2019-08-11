import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
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

    @Nested
    inner class FactoryOrWarehouseLocations {
        @Test
        fun `can create FactoryOrWarehouse location`() {
            Location.FactoryOrWarehouse.instances = 0

            val factory = Location.FactoryOrWarehouse("My factory")

            assertTrue(factory is Location)
            assertTrue(factory is Location.FactoryOrWarehouse)
            assertEquals(100, factory.cost)
            assertEquals(20, factory.rent)
            assertEquals("My factory", factory.name)
        }

        @Test
        fun `number of FactoryOrWarehouse locations is limited`() {
            val factoryOrWarehouseLimit = Location.FactoryOrWarehouse.maxInstances
            Location.FactoryOrWarehouse.instances = 0

            repeat(Location.FactoryOrWarehouse.maxInstances) {
                Location.FactoryOrWarehouse("My factory")
            }

            val e = assertThrows(Exception::class.java) { Location.FactoryOrWarehouse("My factory") }
            assertEquals(
                "Couldn't create location. Maximum of $factoryOrWarehouseLimit factory or warehouse sites allowed.",
                e.message
            )
            assertEquals(Location.FactoryOrWarehouse.maxInstances, Location.FactoryOrWarehouse.instances)
        }
    }

    @Nested
    inner class RetailLocations {
        fun createRetailLocation() = Location.Retail(
            name = "My retail location",
            cost = 300,
            group = Group.BLUE,
            undeveloped = DevelopmentType.RentOnly(10),
            ministore = DevelopmentType.CostAndRent(200, 20),
            supermarket = DevelopmentType.CostAndRent(300, 30),
            megastore = DevelopmentType.CostAndRent(400, 40)
        )

        @Test
        fun `can create Retail location`() {
            Location.Retail.instances = 0
            val retail = createRetailLocation()
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

        @Test
        fun `number of Retail locations is limited`() {
            Location.Retail.instances = 0
            repeat(Location.Retail.maxInstances) {
                createRetailLocation()
            }

            val e = assertThrows(Exception::class.java) { createRetailLocation() }
            assertEquals(
                "Couldn't create location. Maximum of ${Location.Retail.maxInstances} retail sites allowed.",
                e.message
            )
            assertEquals(Location.Retail.maxInstances, Location.Retail.instances)
        }
    }

}
