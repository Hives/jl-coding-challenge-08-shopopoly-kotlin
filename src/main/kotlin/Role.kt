sealed class Role

object Bank : Role()

class Player(val name: String, val board: Board) : Role() {
    var boardLocation = 0
    fun move(roll: DiceRoll) {
        boardLocation = (boardLocation + roll.total()) % board.locations.size
    }
}
