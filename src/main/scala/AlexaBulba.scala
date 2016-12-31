package com.clydemachine.alexabulbapedia

import java.io.IOException
import java.net.SocketTimeoutException

import com.amazon.speech.slu.Intent
import com.amazon.speech.speechlet.{
  IntentRequest,
  LaunchRequest,
  Session,
  SessionEndedRequest,
  SessionStartedRequest,
  Speechlet,
  SpeechletRequest
}
/*
 * Taking heavy direction from: https://github.com/freqlabs/alexa-8ball-scala/
 * Logic flow:
 *
 * Take name from the user.
 * Get ID from name with endpointGetPokeName.
 * Use ID to get evolution information with endpointGetEvolInfo.
 *  Parse evolution information with logic to determine stages, names, levels, special conditions?
 * Return resulting speech string.
 *
 */

class AlexaBulba {
  val endpointGetPokeName = "http://pokeapi.co/api/v2/pokemon/"
  val endpointGetEvolInfo = "http://pokeapi.co/api/v2/evolution-chain/"

  //def get(url: String) = io.Source.fromURL(url).mkString
  def get(url: String,
          connectTimeout: Int = 5000,
          readTimeout: Int = 5000,
          requestMethod: String = "GET") =
  {
    import java.net.{URL, HttpURLConnection}
    val connection = new URL(url).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(connectTimeout)
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod(requestMethod)
    connection.addRequestProperty("User-Agent", "OPR/42.0.2393.94")
    val inputStream = connection.getInputStream
    val content = io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close
    content
  }

  def getEvolutionDetails(pokename: String): String = {
    try {
      println(s"Got $pokename, GETting ID...")
      val pokeID = get(endpointGetPokeName + pokename)
      println(s"Got response! $pokeID \n\nFetching evolution information...")
      // Assume pokeID is extracted...
      val evolInfo = get(endpointGetEvolInfo + "pokeID")
      return evolInfo.mkString("\n")

    } catch {
      case ioexception: IOException => return s"IOException! $ioexception"
      case timeout: SocketTimeoutException => return s"Request timed out! $timeout"
    }
  }
}