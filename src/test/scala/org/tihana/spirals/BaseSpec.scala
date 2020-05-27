package org.tihana.spirals

import org.scalatest.{MustMatchers, WordSpec}
import org.tihana.spirals.distance.{Location, ManhattanDistance, ManhattanDistanceCalculator}

// scalastyle:off magic.number
trait BaseSpec extends WordSpec with MustMatchers {

  val calculator = new ManhattanDistanceCalculator()

  val numbers = Seq(1, 8, 23, 43, 1024, 368078, Int.MaxValue)
  val sizes = Seq(1, 3, 5, 7, 33, 607, 46341)
  val locations = Seq(Location(0, 0), Location(2, 1), Location(4, 2), Location(6, 0), Location(0, 1), Location(606, 235), Location(46340, 41706))
  val manhattanDistances: Seq[ManhattanDistance] = (numbers, locations, sizes).zipped map {
    case (number, location, size) => ManhattanDistance(number, location, size)
  }
  val distances = Seq(0, 1, 2, 6, 31, 371, 41706)
  val invalidNumbers = Seq(0, -50, -1024, Int.MinValue)
  val matrices = Seq(
    "[1]",
    """5   4 3
      |6 [1] 2
      |7 [8] 9""".stripMargin,
    """17 16   15 14 13
      |18  5    4  3 12
      |19  6  [1]  2 11
      |20  7    8  9 10
      |21 22 [23] 24 25""".stripMargin,
    """  37 36 35  34 33 32 31
      |  38 17 16  15 14 13 30
      |  39 18  5   4  3 12 29
      |  40 19  6 [1]  2 11 28
      |  41 20  7   8  9 10 27
      |  42 21 22  23 24 25 26
      |[43] 44 45  46 47 48 49""".stripMargin)
}
