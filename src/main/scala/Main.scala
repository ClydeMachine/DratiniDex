package com.clydemachine.alexabulbapedia

class Main {
  val endpointGetPokeName = "http://pokeapi.co/api/v2/pokemon/"
  val endpointGetEvolInfo = "http://pokeapi.co/api/v2/evolution-chain/"

  def get(url: String) = io.Source.fromURL(url).mkString

  def main(args: Array[String]): Unit = {
    try {
      val content = get("")
    }
  }
}
