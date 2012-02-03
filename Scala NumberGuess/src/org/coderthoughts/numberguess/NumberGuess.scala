package org.coderthoughts.numberguess
import scala.util.Random

object NumberGuess {

  def main(args: Array[String]): Unit = {
    val theNumber = Math.abs(Random.nextInt % 100)
    var guess = -1

    do {
      println("What is your guess?")
      guess = Console.readInt
      if (guess == theNumber)
        println("Yay! you got it right!")
      else if (guess < theNumber)
        println("Try higher")
      else
        println("Try lower")
    } while (guess != theNumber)
  }
}