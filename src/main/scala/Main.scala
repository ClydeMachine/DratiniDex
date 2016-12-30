package com.clydemachine.alexabulbapedia

import java.io.IOException
import java.net.SocketTimeoutException

import com.amazon.speech.slu.Intent

/*
 * Logic flow:
 *
 * Take name from the user.
 * Get ID from name with endpointGetPokeName.
 * Use ID to get evolution information with endpointGetEvolInfo.
 *  Parse evolution information with logic to determine stages, names, levels, special conditions?
 * Return resulting speech string.
 *
 */

class Main {
  val endpointGetPokeName = "http://pokeapi.co/api/v2/pokemon/"
  val endpointGetEvolInfo = "http://pokeapi.co/api/v2/evolution-chain/"

  def get(url: String) = io.Source.fromURL(url).mkString

  def getEvolutionDetails(args: Array[String]): Unit = {
    try {
      val pokeID = get(endpointGetPokeName + args(0))
      val evolInfo = get(endpointGetEvolInfo + pokeID)
    } catch {
      case ioexception: IOException => println(s"IOException! $ioexception")
      case timeout: SocketTimeoutException => println(s"Request timed out! $timeout")
    }
  }

}
