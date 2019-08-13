import java.lang.Math.abs

class GBP(val _value: Int) {
    val value: Int

    init {
        value = abs(_value)
    }

    override fun equals(other: Any?) : Boolean = other is GBP && other.value == this.value
}
