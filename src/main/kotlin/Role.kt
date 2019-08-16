sealed class Role

object Bank : Role()

class Player(val name: String, val board: Board) : Role() {
    var boardLocation = 0
    fun move(spaces: Int) {
        boardLocation = (boardLocation + spaces) % board.locations.size
    }
}
