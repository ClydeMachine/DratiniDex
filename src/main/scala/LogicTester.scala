package com.clydemachine.alexabulbapedia

object LogicTester extends AlexaBulba {
  def main(args: Array[String]): Unit = {
    // Run tests of the application logic. This is for dev testing before Alexa integration.
    // Test with kangaskhan for no evolutions, abra for a pretty standard evolution, gengar for a final evolution.
    println(getEvolutionDetails("dratini"))
  }
}