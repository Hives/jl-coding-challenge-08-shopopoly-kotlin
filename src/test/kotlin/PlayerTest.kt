import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PlayerTest {
    val board = Board(List(13) { Location.FreeParking })
    @Test
    fun `player starts on first square`() {
        val player = Player("Paul", board)
        assertEquals(0, player.boardLocation)
    }
    @Test
    fun `player can move 2 spaces` () {
        val player = Player("Paul", board)
        player.move(DiceRoll(1, 1))
        assertEquals(2,player.boardLocation)
    }

    @Test
    fun `player can wrap around the board`() {
        val player = Player("Paul", board)
        player.move(DiceRoll(6, 6))
        player.move(DiceRoll(1, 1))
        assertEquals(1, player.boardLocation)
    }

}
