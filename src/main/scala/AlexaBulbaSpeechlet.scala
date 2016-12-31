package com.clydemachine.alexabulbapedia

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
import com.clydemachine.alexabulbapedia.speech.speechlet.SpeechletResponse
import com.clydemachine.alexabulbapedia.speech.ui.PlainTextOutputSpeech

class AlexaBulbaSpeechlet extends Speechlet {
  import AlexaBulbaSpeechlet._

  override def onSessionStarted(request: SessionStartedRequest, session: Session): Unit = {
    logInvocation("onSessionStarted", request, session)
  }

  override def onLaunch(request: LaunchRequest, session: Session): SpeechletResponse = {
    logInvocation("onLaunch", request, session)
    val outputSpeech = new PlainTextOutputSpeech("Which Poke'mon do you wish to know about? ")
    new SpeechletResponse(outputSpeech)
  }

  override def onIntent(request: IntentRequest, session: Session): SpeechletResponse = {
    logInvocation("onIntent", request, session)
    // This is where we actually handle the user's question and provide a response.
    val ReceivedPokeName = "abra".toLowerCase
    val PokeEvolutionInfoAnswer = AlexaBulba.getEvolutionDetails(ReceivedPokeName)
    val outputSpeech = new PlainTextOutputSpeech(PokeEvolutionInfoAnswer)
    new SpeechletResponse(outputSpeech)
  }

  override def onSessionEnded(request: SessionEndedRequest, session: Session): Unit = {
    logInvocation("onSessionEnded", request, session)
  }
}

object AlexaBulbaSpeechlet {
  private def logInvocation(name: String, request: SpeechletRequest, session: Session): Unit = {
    val requestID = request.getRequestId
    val sessionID = session.getSessionId
    println(s"Debug: $name; Request: $request, Session: $session")
  }
}
