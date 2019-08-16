import kotlin.random.Random

class DiceRoll(val first: Int, val second: Int) {
    fun total() = first + second
}

fun rollDice(rng: Random): DiceRoll = DiceRoll(
    rng.nextInt(1,6),
    rng.nextInt(1,6)
)
