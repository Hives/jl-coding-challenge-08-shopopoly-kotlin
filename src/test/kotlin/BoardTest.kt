import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class BoardTest {
    val freeParkingSpaces = List(12) { Location.FreeParking }
    val spaces = listOf(Location.Go) + freeParkingSpaces
    val board = Board(spaces)

    @Test
    fun `we can tell if a player does not pass go`() {
        assertFalse(board.doesMovePassGo(0, 2))
        assertFalse(board.doesMovePassGo(0, 12))
        assertFalse(board.doesMovePassGo(9, 2))
    }

    @Test
    fun `we can tell if a player does pass go`() {
        assertTrue(board.doesMovePassGo(11, 2))
        assertTrue(board.doesMovePassGo(1, 12))
    }
}
