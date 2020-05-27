package org.tihana.spirals.distance

import org.scalatest.TryValues._
import org.tihana.spirals.BaseSpec

class ManhattanDistanceCalculatorSpec extends BaseSpec {

  "calculate minimum grid size" must {
    (numbers, sizes).zipped foreach { (number, size) =>
      s"return $size for number $number" in {
        calculator.minGridSize(number) mustEqual size
      }
    }
  }

  "calculate location" must {
    (numbers, locations).zipped foreach { case (number, location) =>
      s"return $location for number $number" in {
        val size = calculator.minGridSize(number)
        calculator.locationForNumber(number, size) mustEqual location
      }
    }
  }

  "calculate distance" must {
    (manhattanDistances, distances).zipped foreach { case (manhattanDistance, distance) =>
      s"return $distance for number ${manhattanDistance.number}" in {
        val maybeManhattanDistance = calculator.calculate(manhattanDistance.number)
        maybeManhattanDistance.isSuccess mustBe true
        maybeManhattanDistance.success.value mustEqual manhattanDistance
        maybeManhattanDistance.success.value.distance mustEqual distance
      }
    }
    invalidNumbers foreach { number =>
      s"return failure with ${classOf[IllegalArgumentException].getSimpleName} for number $number" in {
        val maybeManhattanDistance = calculator.calculate(number)
        maybeManhattanDistance.isFailure mustBe true
        maybeManhattanDistance.failure.exception mustBe a[IllegalArgumentException]
        maybeManhattanDistance.failure.exception.getMessage mustEqual "Number must be greater than 0!"
      }
    }
  }
}
