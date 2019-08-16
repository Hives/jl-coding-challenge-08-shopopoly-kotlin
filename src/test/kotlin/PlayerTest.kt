import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PlayerTest {
    val board = Board(20)
    @Test
    fun `player starts on first square`() {
        val player = Player("Paul", board)
        assertEquals(0, player.boardLocation)
    }
    @Test
    fun `player can move 2 spaces` () {
        val player = Player("Paul", board)
        player.move(2)
        assertEquals(2,player.boardLocation)
    }

    @Test
    fun `player can pass go`() {
        val player = Player("Paul", board)
        player.move(21)
        assertEquals(1, player.boardLocation)
    }

}
