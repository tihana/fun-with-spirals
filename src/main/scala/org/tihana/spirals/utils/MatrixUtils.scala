package org.tihana.spirals.utils

import org.tihana.spirals.distance.{Location, ManhattanDistance}

object MatrixUtils {

  /**
   * Prints out the following to standard output:
   *  - Manhattan distance to the center (in a spiral matrix) for a specified number
   *  - number of steps required in each direction (left or right, up or down) to reach the center
   *  - grid size (minimum number of rows/columns required for the spiral matrix to contain the specified number)
   *  - location (row and column) of the number for which Manhattan distance to the center was calculated
   *  - location (row and column) of the center of the spiral matrix
   *  - spiral matrix (of size up to 35x35) containing the number for which Manhattan distance was calculated
   *
   * @param manhattanDistance the object containing a number (for which Manhattan distance was calculated), its location (row and column) in the spiral matrix,
   *                          Manhattan distance to the center of the matrix, size of the matrix and location (row and column) of the center of the matrix
   */
  def printDistance(manhattanDistance: ManhattanDistance): Unit = {
    print(s"From location ${manhattanDistance.number} the distance is ${manhattanDistance.distance}")
    printSteps(manhattanDistance)
    println(s"Grid size: ${manhattanDistance.size} x ${manhattanDistance.size}, location: ${manhattanDistance.location}, center: ${manhattanDistance.center}")
    printSpiralMatrix(manhattanDistance)
  }

  /**
   * Prints out to standard output number of steps required in each direction (left or right, up or down) to reach the center of the spiral matrix
   * from the specified number (for which Manhattan distance was calculated).
   *
   * @param manhattanDistance the object containing a number (for which Manhattan distance was calculated), its location (row and column) in the spiral matrix,
   *                          Manhattan distance to the center of the matrix, size of the matrix and location (row and column) of the center of the matrix
   */
  private def printSteps(manhattanDistance: ManhattanDistance): Unit = {
    val columnDiff = manhattanDistance.location.column - manhattanDistance.center.column
    val rightLeft = if (columnDiff > 0) Option(s"$columnDiff left") else if (columnDiff < 0) Option(s"${Math.abs(columnDiff)} right") else None
    val rowDiff = manhattanDistance.location.row - manhattanDistance.center.row
    val upDown = if (rowDiff > 0) Option(s"$rowDiff up") else if (rowDiff < 0) Option(s"${Math.abs(rowDiff)} down") else None
    Seq(rightLeft, upDown).flatten.mkString(", ") match {
      case steps: String if steps.nonEmpty => println(s" ($steps)")
      case _ => println()
    }
  }

  /**
   * Prints out spiral matrix (of size up to 35x35) to standard output.
   * Marks (with square brackets) the number in the center of the matrix and the number for which we're calculating Manhattan distance to the center.
   *
   * @param manhattanDistance the object containing a number (for which Manhattan distance was calculated), its location (row and column) in the spiral matrix,
   *                          Manhattan distance to the center of the matrix, size of the matrix and location (row and column) of the center of the matrix
   */
  private def printSpiralMatrix(manhattanDistance: ManhattanDistance): Unit = {
    if (manhattanDistance.size <= 35) {
      // size of the matrix (number of rows/columns)
      val size = manhattanDistance.size
      // number of digits in the largest number in the matrix (required for padding/aligning)
      val maxDigits = Math.pow(size, 2).toInt.toString.length
      (0 until size) foreach { row =>
        (0 until size) foreach { column =>
          // layer where the number we're printing out is (center of the matrix is layer 1, next layer towards the outside is 2 etc)
          val layer = Math.max(Math.abs(size / 2 - row), Math.abs(size / 2 - column))
          // size of the matrix (number of rows/columns) for the layer where the number we're printing out is
          val sizeForLayer = 1 + 2 * layer
          // starting (smallest) number in the layer
          val startForLayer = Math.pow(sizeForLayer, 2).toInt - Math.max(1, 8 * layer) + 1
          // number in the top left corner of the layer
          val topLeft = Math.max(1, startForLayer + 2 * sizeForLayer - 3)
          // offset from the edge (last/biggest layer) of the matrix
          val offset = Math.min(Math.min(row, column), Math.min(size - 1 - row, size - 1 - column))
          val number = if (row >= column) {
            topLeft + (row - offset) + (column - offset) // lower left half
          } else {
            topLeft - (row - offset) - (column - offset) // upper right half
          }
          printNumber(number, Location(row, column), manhattanDistance, maxDigits)
        }
        println()
      }
    } else {
      println("(spiral matrix too big to print out)")
    }
  }

  /**
   * Prints out `number` to standard output.
   * Marks (with square brackets) the number in the center of the matrix and the number for which Manhattan distance to the center was calculated.
   *
   * @param number            the number to print out
   * @param location          the location (row and column) of `number` in the spiral matrix
   * @param manhattanDistance the object containing a number (for which Manhattan distance was calculated), its location (row and column) in the spiral matrix,
   *                          Manhattan distance to the center of the matrix, size of the matrix and location (row and column) of the center of the matrix
   * @param maxDigits         the number of digits of the largest number in the matrix (required for padding/aligning)
   */
  private def printNumber(number: Int, location: Location, manhattanDistance: ManhattanDistance, maxDigits: Int): Unit = {
    val padLeft = leftPadding(location, manhattanDistance, maxDigits)
    val formattedNumber = location match {
      case location: Location if location == manhattanDistance.location || location == manhattanDistance.center => s"[$number]"
      case _ => number.toString
    }
    print(formattedNumber.reverse.padTo(padLeft, ' ').reverse)
  }

  /**
   * Calculates padding needed when printing out number on location (row and column) `location`.
   *
   * @param location          the location (row and column) of the number to print out
   * @param manhattanDistance the object containing a number (for which Manhattan distance was calculated), its location (row and column) in the spiral matrix,
   *                          Manhattan distance to the center of the matrix, size of the matrix and location (row and column) of the center of the matrix
   * @param maxDigits         the number of digits of the largest number in the spiral matrix
   * @return padding needed to print out number on `location`
   */
  private def leftPadding(location: Location, manhattanDistance: ManhattanDistance, maxDigits: Int): Int = {
    val maxWidth = location match {
      // if the number to print out is in the same column as both the center of the matrix and the number for which Manhattan distance was calculated,
      // width should be maximum of:
      // - 3, represents the length of "[1]" - number in the center of the matrix (marked with square brackets)
      // - length (number of digits) of the largest number in the matrix
      // - length (number of digits) of number for which Manhattan distance was calculated (marked with square brackets)
      case Location(_, column) if column == manhattanDistance.location.column && column == manhattanDistance.center.column =>
        Math.max(Math.max(3, maxDigits), manhattanDistance.number.toString.length + 2)
      // if the number to print out is in the same column as the number for which Manhattan distance was calculated, width should be maximum of:
      // - length (number of digits) of the largest number in the matrix
      // - length (number of digits) of number for which Manhattan distance was calculated (marked with square brackets)
      case Location(_, column) if column == manhattanDistance.location.column =>
        Math.max(maxDigits, manhattanDistance.number.toString.length + 2)
      // if the number to print out is in the same column as the center of the matrix, width should be maximum of:
      // - 3, represents the length of "[1]" - number in the center of the matrix (marked with square brackets)
      // - length (number of digits) of the largest number in the matrix
      case Location(_, column) if column == manhattanDistance.center.column =>
        Math.max(3, maxDigits)
      // for all other cases, width should be the length (number of digits) of the largest number in the matrix
      case _ => maxDigits
    }
    // padding should be maximum width + 1 (for all columns except the first one)
    maxWidth + Math.min(1, location.column)
  }
}
