import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class BoardTest {
    val freeParkingSpaces = List(12) { Location.FreeParking }
    val goAtStartSpaces = listOf(Location.Go) + freeParkingSpaces
    val goAtStartBoard = Board(goAtStartSpaces)

    val goAtEndSpaces = freeParkingSpaces + listOf(Location.Go)
    val goAtEndBoard = Board(goAtEndSpaces)

    @Test
    fun `we can tell if a player does not pass go`() {
        assertFalse(goAtStartBoard.doesMovePassGo(0, 2))
        assertFalse(goAtStartBoard.doesMovePassGo(0, 12))
        assertFalse(goAtStartBoard.doesMovePassGo(9, 2))
        assertFalse(goAtEndBoard.doesMovePassGo(0,11))
        assertFalse(goAtEndBoard.doesMovePassGo(9, 2))
    }

    @Test
    fun `we can tell if a player does pass go`() {
        assertTrue(goAtStartBoard.doesMovePassGo(11, 2))
        assertTrue(goAtStartBoard.doesMovePassGo(1, 12))
        assertTrue(goAtEndBoard.doesMovePassGo(10, 5))
    }
}
