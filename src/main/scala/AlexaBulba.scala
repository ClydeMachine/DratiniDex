package com.clydemachine.alexabulbapedia

import java.io.IOException
import java.net.SocketTimeoutException

import play.api.libs.json._

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

  val endpointGetPokeName = "http://pokeapi.co/api/v2/pokemon-species/"
  val endpointGetEvolInfo = "http://pokeapi.co/api/v2/evolution-chain/"

  def get(url: String, connectTimeout: Int = 5000, readTimeout: Int = 5000, requestMethod: String = "GET"): String =  {
    import java.net.{URL, HttpURLConnection}
    val connection = new URL(url).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(connectTimeout)
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod(requestMethod)
    connection.addRequestProperty("User-Agent", "OPR/42.0.2393.94") // Should probably make a unique User-Agent string for this application.
    val inputStream = connection.getInputStream
    val content = io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close
    content
  }

  def getEvolutionDetails(pokename: String): String = {
    try {
      val PokemonSpeciesDetails = Json.parse(get(endpointGetPokeName + pokename))
      val PokemonEvolutionChainURL = ((PokemonSpeciesDetails \ "evolution_chain" \ "url").get).as[String]
      println(s"Got evolution chain, fetching from $PokemonEvolutionChainURL...")
      val PokemonEvolutionChainDetails = Json.parse(get(PokemonEvolutionChainURL))

      // Does this evolution chain contain evolution stages?


      return "Evolution information result returned."
    } catch {
      case ioexception: IOException => return s"IOException! $ioexception"
      case timeout: SocketTimeoutException => return s"Request timed out! $timeout"
    }
  }
}