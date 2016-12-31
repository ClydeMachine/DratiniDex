package com.clydemachine.alexabulbapedia

/** This code by ClydeMachine - clydemachine@gmail.com */
object LogicTester {
  def main(args: Array[String]): Unit = {
    // Run tests of the application logic. This is for dev testing before Alexa integration.
    // Test with kangaskhan for no evolutions, abra for a pretty standard evolution, gengar for a final evolution.
    println(AlexaBulba.getEvolutionDetails("dratini"))
  }
}
