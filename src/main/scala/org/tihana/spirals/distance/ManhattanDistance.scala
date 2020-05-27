package org.tihana.spirals.distance

/**
 * Contains the following information about Manhattan distance between a number and the center in a two-dimensional spiral matrix:
 *  - number for which Manhattan distance was calculated
 *  - location (row and column) of that number in the spiral matrix
 *  - size of the spiral matrix (number of rows/columns)
 *  - location (row and column) of the center in the spiral matrix
 *  - Manhattan distance between the number and the center in the spiral matrix
 *
 * @param number   the number for which Manhattan distance to the center (in a spiral matrix) was calculated
 * @param location the location (row and column) of `number` in the spiral matrix
 * @param size     the size of the spiral matrix (number of rows/columns)
 */
case class ManhattanDistance(number: Int,
                             location: Location,
                             size: Int) {

  /**
   * The location (row and column) of the center in a spiral matrix.
   */
  val center: Location = Location(size / 2, size / 2)

  /**
   * The Manhattan distance between `number` and the center in a spiral matrix.
   */
  val distance: Int = Math.abs(location.row - center.row) + Math.abs(location.column - center.column)
}

/**
 * Represents a location (row and column) in a two-dimensional spiral matrix.
 *
 * @param row    the row in a spiral matrix
 * @param column the column in a spiral matrix
 */
case class Location(row: Int,
                    column: Int) {
  override def toString: String = s"[$row, $column]"
}
