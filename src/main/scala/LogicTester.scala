package com.clydemachine.alexabulbapedia

object LogicTester extends AlexaBulba {
  def main(args: Array[String]): Unit = {
    // Run tests of the above logic. Later we'll integrate it into the Alexa skill structure.
    // Test with kangaskhan for no evolutions, abra for a pretty standard evolution, gengar for a final evolution.
    println(getEvolutionDetails("kangaskhan"))
  }
}