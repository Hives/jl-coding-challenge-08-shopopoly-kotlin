import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class GBPTest {
    @Test
    fun `positive value is unchanged`() {
        val tenPounds = GBP(10)
        assertEquals(10, tenPounds.value)
    }

    @Test
    fun `negative value is converted to positive`() {
        val tenPounds = GBP(-10)
        assertEquals(10, tenPounds.value)
    }

    @Test
    fun `values can be equal`() {
        assert(GBP(10) == GBP(10))
    }

    @Test
    fun `values can be unequal`() {
        assert(GBP(10) != GBP(11))
    }
}
