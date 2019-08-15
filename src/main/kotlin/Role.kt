sealed class Role

data class Player(val name: String) : Role()
object Bank : Role()
