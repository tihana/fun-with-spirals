package org.tihana.spirals

import org.tihana.spirals.distance.ManhattanDistanceCalculator
import org.tihana.spirals.distance.ManhattanDistanceCalculator.DefaultNumber
import org.tihana.spirals.utils.MatrixUtils

import scala.Console.err
import scala.io.StdIn
import scala.util.{Failure, Success, Try}

/**
 * '''Fun with spirals'''
 *
 * Interactive command line interface to calculate the Manhattan distance between
 * a given number (located in one of the cells) and the center in a two-dimensional spiral matrix.
 */
object FunWithSpirals extends App {

  /**
   * Manhattan distance calculator.
   */
  private val calculator = ManhattanDistanceCalculator()

  /**
   * List of available options of the app.
   */
  private val options =
    s""" - a number, must be a positive integer between 1 and ${Int.MaxValue}
       | - nothing, defaults the number to $DefaultNumber
       | - help (case insensitive), prints out all available options of the app
       | - exit (case insensitive), exits the app""".stripMargin

  println(
    """ _____                       _ _   _                 _           _
      ||  ___|   _ _ __   __      _(_) |_| |__    ___ _ __ (_)_ __ __ _| |___
      || |_ | | | | '_ \  \ \ /\ / / | __| '_ \  / __| '_ \| | '__/ _` | / __|
      ||  _|| |_| | | | |  \ V  V /| | |_| | | | \__ \ |_) | | | | (_| | \__ \
      ||_|   \__,_|_| |_|   \_/\_/ |_|\__|_| |_| |___/ .__/|_|_|  \__,_|_|___/
      |                                              |_|""".stripMargin)
  help()
  run()

  /**
   * Run the interactive command line interface of the app.
   */
  private def run(): Unit = {
    while (true) {
      println()
      println("Enter a number (one of the cell values) in a spiral matrix for which you want to calculate the Manhattan distance to the center:")
      println("""(enter "help" to print out help, enter "exit" to exit the app)""")
      Option(StdIn.readLine).map(_.trim.toLowerCase) match {
        case Some("help") => help()
        case Some("exit") => exit()
        case Some(input) if input.nonEmpty =>
          Try(input.toInt) match {
            case Success(number) if number > 0 => calculate(number)
            case _ => invalid(input)
          }
        case _ =>
          println(s"Number not specified, defaulting to $DefaultNumber")
          calculate(DefaultNumber)
      }
    }
  }

  /**
   * Calculates Manhattan distance between the location of `number` and the center in a spiral matrix.
   * Prints out the result to standard output.
   *
   * @param number the number to calculate Manhattan distance for
   */
  private def calculate(number: Int): Unit = {
    calculator.calculate(number) match {
      case Success(manhattanDistance) => MatrixUtils.printDistance(manhattanDistance)
      case Failure(f) => err.println(s"Oh no! Something went wrong. Here's some info about the error: ${f.getMessage}")
    }
  }

  /**
   * Prints out (to standard output) all available options of the app.
   */
  private def help(): Unit = {
    println()
    println("Welcome to Fun with spirals!")
    println("Here you can calculate the Manhattan distance between a number (located in one of the cells) and the center in a spiral matrix.")
    println("The middle (center) of the matrix starts with 1 and the numbers are monotone increased counter-clockwise to the outside.")
    println("You can enter one of the following:")
    println(options)
  }

  /**
   * Prints out (to standard error output) entered (invalid) input and lists all available options of the app.
   *
   * @param input the entered (invalid) input
   */
  private def invalid(input: String): Unit = {
    err.println(s"Invalid input '$input', allowed options are:")
    err.println(options)
  }

  /**
   * Exits the app.
   */
  private def exit(): Unit = {
    println(
      """ ____             _
        || __ ) _   _  ___| |
        ||  _ \| | | |/ _ \ |
        || |_) | |_| |  __/_|
        ||____/ \__, |\___(_)
        |       |___/""".stripMargin)
    sys.exit()
  }
}
