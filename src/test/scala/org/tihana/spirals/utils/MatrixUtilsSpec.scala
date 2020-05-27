package org.tihana.spirals.utils

import java.io.ByteArrayOutputStream

import org.tihana.spirals.BaseSpec

import scala.util.matching.Regex

class MatrixUtilsSpec extends BaseSpec {

  "print distance" must {
    (manhattanDistances.take(matrices.size), matrices).zipped foreach { case (manhattanDistance, matrix) =>
      s"print out distance for number ${manhattanDistance.number}" in {
        val output = getStandardOutput(MatrixUtils.printDistance(manhattanDistance))
        output must startWith regex s"From location ${manhattanDistance.number} the distance is ${manhattanDistance.distance}"
        output must include regex s"Grid size: ${manhattanDistance.size} x ${manhattanDistance.size}, " +
          s"location: ${Regex.quote(manhattanDistance.location.toString)}, center: ${Regex.quote(manhattanDistance.center.toString)}"
        output must endWith regex Regex.quote(matrix)
      }
    }
  }

  private def getStandardOutput(fn: => Unit): String = {
    val stream = new ByteArrayOutputStream
    Console.withOut(stream)(fn)
    stream.toString.trim
  }
}
