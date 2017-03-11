package com.clydemachine.dratinidex

import com.amazon.speech.slu.{Intent, Slot}
import com.clydemachine.dratinidex.speech.speechlet.SpeechletResponse
import com.amazon.speech.speechlet._
import com.clydemachine.dratinidex.speech.ui.PlainTextOutputSpeech

class DratiniDexSpeechlet extends Speechlet {
  import DratiniDexSpeechlet._

  val introText =
    """
      | This skill gives evolution information for almost all Poke'mon in existence.
      | Ask something like, what does Abra evolve into, and I'll give you what I know.
      | Try asking about a specific Poke'mon!
    """.stripMargin

  val helpText =
    """
      | This skill gives evolution information for almost all Poke'mon in existence.
      | Ask something like, what does Abra evolve into, and I'll give you what I know.
      | What Poke'mon would you like to know about?
    """.stripMargin

  override def onSessionStarted(request: SessionStartedRequest, session: Session): Unit = {
    logInvocation("onSessionStarted", request, session)
  }

  override def onLaunch(request: LaunchRequest, session: Session): SpeechletResponse = {
    logInvocation("onLaunch", request, session)
    val outputSpeech = new PlainTextOutputSpeech(introText)
    new SpeechletResponse(outputSpeech=outputSpeech, shouldEndSession=false)
  }

  override def onIntent(request: IntentRequest, session: Session): SpeechletResponse = {
    logInvocation("onIntent", request, session)

    val intent: Intent = request.getIntent
    val intentName: String = intent.getName

    // DEBUG
    logInvocation(s"onIntent detected intent: $intentName", request, session)

    if (intentName == "GetPokemonEvolution") {
      // This is where we actually handle the user's question and provide a response.
      var ReceivedPokeName: String = ""
      try {
        ReceivedPokeName = request.getIntent.getSlot("pokemonname").getValue.toLowerCase
      } catch {
        case e: Exception =>
          val intentInfo = intent.toString
          val errorResponse =
            s"""
              | I'm having trouble parsing that Pokemon name.
              | This usually happens if a particular phrase wasn't heard correctly - try rephrasing your question.
              | For debugging purposes, the error I got was $e.
            """.stripMargin
          return new SpeechletResponse(new PlainTextOutputSpeech(errorResponse))
      }
      logInvocation(s"onIntent detected pokemonname: $ReceivedPokeName", request, session)
      val PokeEvolutionInfoAnswer = DratiniDex.getEvolutionDetails(ReceivedPokeName)
      val outputSpeech = new PlainTextOutputSpeech(PokeEvolutionInfoAnswer)
      new SpeechletResponse(outputSpeech)

    } else if (intentName == "AMAZON.HelpIntent") {
      logInvocation(s"onIntent detected $intentName", request, session)
      val outputSpeech = new PlainTextOutputSpeech(helpText)
      new SpeechletResponse(outputSpeech=outputSpeech, shouldEndSession=false)

    } else if (intentName == "AMAZON.CancelIntent") {
      logInvocation(s"onIntent detected $intentName, cancelling.", request, session)
      val outputSpeech = new PlainTextOutputSpeech("Until next time! ")
      new SpeechletResponse(outputSpeech)

    } else {
      logInvocation("onIntent didn't match an intent, exiting.", request, session)
      val outputSpeech = new PlainTextOutputSpeech("Until next time! ")
      new SpeechletResponse(outputSpeech)
    }
  }

  override def onSessionEnded(request: SessionEndedRequest, session: Session): Unit = {
    logInvocation("onSessionEnded", request, session)
  }
}

object DratiniDexSpeechlet {
  private def logInvocation(name: String, request: SpeechletRequest, session: Session): Unit = {
    val requestID = request.getRequestId
    val sessionID = session.getSessionId
    println(s"Debug: $name; Request: $request, Session: $session")
  }
}
