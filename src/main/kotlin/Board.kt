class Board(val locations: List<Location>) {
    fun doesMovePassGo(startingSpace: Int, spaces: Int): Boolean {
        val checkFrom = startingSpace + 1
        val checkTo = startingSpace + spaces
        val spacesVisited =
            (checkFrom..checkTo)
            .toSet()
            .map { it % locations.size }

        return locations.slice(spacesVisited).contains(Location.Go)
    }
}
