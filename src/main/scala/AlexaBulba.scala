package com.clydemachine.alexabulbapedia

import java.io.IOException
import java.net.SocketTimeoutException

import play.api.libs.json._
import com.lambdaworks.jacks._

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

  def get(url: String, connectTimeout: Int = 10000, readTimeout: Int = 10000, requestMethod: String = "GET"): String =  {
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
      val PokemonEvolutionChainDetails = Json.parse(get(PokemonEvolutionChainURL))

      // Does this evolution chain contain evolution stages?
      val Chain = (PokemonEvolutionChainDetails \ "chain").get.toString

      // Convert Chain to a Scala map.
      val EvolutionMap = JacksMapper.readValue[Map[String,Any]](Chain)

      var response = ""

      // Check for details of the first evolved state.
      if (EvolutionMap("evolves_to") != List()) {
        val EvolutionDetails_FirstEvolution_Map: Map[String,Any] = EvolutionMap("evolves_to").asInstanceOf[List[Any]](0).asInstanceOf[Map[String,Any]]
        val FirstEvolution_StartingFormName: String = EvolutionMap("species").asInstanceOf[Map[String,String]]("name")
        response += "It appears " + FirstEvolution_StartingFormName + " evolves "

        val FirstEvolution_Level = EvolutionDetails_FirstEvolution_Map("evolution_details").asInstanceOf[List[Any]](0).asInstanceOf[Map[String,Any]]("min_level")
        if (FirstEvolution_Level != null) response += s"at level $FirstEvolution_Level "

        val FirstEvolution_Name = EvolutionDetails_FirstEvolution_Map("species").asInstanceOf[Map[String,Any]]("name")
        if (FirstEvolution_Name != null) response += s"into $FirstEvolution_Name"

        // Check for and prepare for a second evolution stage.
        if (EvolutionDetails_FirstEvolution_Map("evolves_to") != List()) {

          val EvolutionDetails_SecondEvolution_Map: Map[String,Any] = EvolutionDetails_FirstEvolution_Map("evolves_to").asInstanceOf[List[Any]](0).asInstanceOf[Map[String,Any]]
          response += ", and then evolves again "

          val SecondEvolution_Level = EvolutionDetails_SecondEvolution_Map("evolution_details").asInstanceOf[List[Any]](0).asInstanceOf[Map[String,Any]]("min_level")
          if (SecondEvolution_Level != null) {
            response += s"at level $SecondEvolution_Level "
          } else {
            response += "through a means other than levelling up, "
          }

          val SecondEvolution_Name = EvolutionDetails_SecondEvolution_Map("species").asInstanceOf[Map[String,Any]]("name")
          if (SecondEvolution_Name != null) {
            response += s"into $SecondEvolution_Name"
          } else {
            response += "but I don't seem to have a name for that final evolution. Sorry about that"
          }

        }
        response += ". "
      } else {
        response = "It appears this Poke'mon doesn't evolve into or from anything else. "
      }
      return response
    } catch {
      case ioexception: IOException => return s"IOException! $ioexception"
      case timeout: SocketTimeoutException => return s"Request timed out! $timeout"
    }
  }
}