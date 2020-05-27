package org.tihana.spirals.distance

import scala.util.Try

/**
 * Calculates Manhattan distance between a number (located in one of the cells) and the center of a two-dimensional spiral matrix.
 * The middle (center) of the matrix starts with 1 and the numbers are monotone increased counter-clockwise to the outside.
 */
class ManhattanDistanceCalculator {

  /**
   * Calculates Manhattan distance between the location of `number` and the center in a spiral matrix.
   *
   * @param number the number to calculate Manhattan distance for, must be greater than 0
   * @return object containing the number (for which Manhattan distance was calculated), its location (row and column) in the spiral matrix,
   *         Manhattan distance to the center of the matrix, size of the matrix and location (row and column) of the center of the matrix
   */
  def calculate(number: Int): Try[ManhattanDistance] = Try {
    if (number < 1) {
      throw new IllegalArgumentException("Number must be greater than 0!")
    }
    val size = minGridSize(number)
    val location = locationForNumber(number, size)
    ManhattanDistance(number, location, size)
  }

  /**
   * Determines minimum size of the spiral matrix (number of rows/columns) that contains `number`.
   *
   * @param number the number (one of the cell values) in a spiral matrix
   * @return minimum size of the spiral matrix (number of rows/columns) that contains `number`
   */
  private[spirals] def minGridSize(number: Int): Int = {
    Math.ceil(Math.sqrt(number)).toInt match {
      case sqrt: Int if sqrt % 2 == 0 => sqrt + 1
      case sqrt: Int => sqrt
    }
  }

  /**
   * Calculates the location (row and column) of `number` in a spiral matrix of size (number of rows/columns) `size`.
   *
   * @param number the number to find the location for (row and column) in the spiral matrix
   * @param size   the size of the matrix (number of rows/columns)
   * @return row and column (zero-based array indexing) of the specified number in the matrix
   */
  private[spirals] def locationForNumber(number: Int, size: Int): Location = {
    val maxNumber = BigInt(size).pow(2) // largest number in the matrix
    if (number <= maxNumber && number > maxNumber - size) {
      // number is in the last row, calculate column
      val column = size - (maxNumber - number) - 1
      Location(size - 1, column.toInt)
    } else if (number <= maxNumber - size && number > maxNumber - 2 * size + 2) {
      // number is in the first column (excluding elements in first and last row), calculate row
      val row = number - (maxNumber - 2 * size + 2)
      Location(row.toInt, 0)
    } else if (number <= maxNumber - 2 * size + 2 && number > maxNumber - 3 * size + 2) {
      // number is in the first row, calculate column
      val column = maxNumber - 2 * size + 2 - number
      Location(0, column.toInt)
    } else {
      // number is in the last column (excluding elements in first and last row), calculate row
      val row = maxNumber - 3 * size + 3 - number
      Location(row.toInt, size - 1)
    }
  }
}

object ManhattanDistanceCalculator {

  /**
   * Default number that can be used to calculate Manhattan distance when user doesn't specify one.
   */
  val DefaultNumber = 368078

  def apply(): ManhattanDistanceCalculator = new ManhattanDistanceCalculator()
}
