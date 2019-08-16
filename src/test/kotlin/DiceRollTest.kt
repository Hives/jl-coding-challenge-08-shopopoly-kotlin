import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

internal class DiceRollTest {

    val mockRandomNumberGenerator = mock<Random>()

    @Test
    fun `random number between 1 and 6 is generated`() {
        rollDice(mockRandomNumberGenerator)
        verify(mockRandomNumberGenerator, times(2)).nextInt(1,6)
    }

    @Test
    fun `dice roll total adds up dice`() {
        val diceRoll = DiceRoll(1, 2)
        assertEquals(3, diceRoll.total())
    }
}
