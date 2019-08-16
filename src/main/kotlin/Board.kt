class Board(val locations: List<Location>) {
    fun doesMovePassGo(start: Int, spaces: Int): Boolean {
        val startingSpace = start + 1
        val endingSpace = (start + spaces) % locations.size

        return if (endingSpace > startingSpace) {
            locations.slice(startingSpace..endingSpace).contains(Location.Go)
        } else {
            locations.slice(startingSpace..locations.size - 1).contains(Location.Go) ||
                    locations.slice(0..endingSpace).contains(Location.Go)
        }
    }
}
