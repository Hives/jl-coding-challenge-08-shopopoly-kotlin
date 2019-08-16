sealed class Role

object Bank : Role()

class Player(val name: String, val board: Board, var boardLocation: Int = 0) : Role() {
    fun move(spaces: Int) {
        boardLocation = (boardLocation + spaces) % board.locations.size
    }
}
