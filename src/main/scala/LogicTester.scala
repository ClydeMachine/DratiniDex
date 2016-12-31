package com.clydemachine.alexabulbapedia

object LogicTester extends AlexaBulba {
  def main(args: Array[String]): Unit = {
    // Run tests of the above logic. Later we'll integrate it into the Alexa skill structure.
    println(getEvolutionDetails("abra"))
  }
}