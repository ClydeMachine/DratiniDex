package com.clydemachine.alexabulbapedia

import com.amazon.speech.speechlet.{SessionEndedRequest, _}
import com.clydemachine.alexabulbapedia.PlainTextOutputSpeech

class BulbapediaSpeechlet extends Speechlet {
  import BulbapediaSpeechlet._

  override def onSessionStarted(request: SessionStartedRequest, session: Session): Unit = {
    logInvocation("onSessionStarted", request, session)
  }

  override def onLaunch(request: LaunchRequest, session: Session): SpeechletResponse = {
    logInvocation("onLaunch", request, session)

    val outputSpeech = new PlainTextOutputSpeech("Which Poke'mon are you curious about?")

    new SpeechletResponse(outputSpeech)
  }

  override def onIntent(request: IntentRequest, session: Session): SpeechletResponse = {
    logInvocation("onIntent", request, session)

    val answer = Bulbapedia.ask()
    val outputSpeech = new PlainTextOutputSpeech(answer)

    new SpeechletResponse(outputSpeech)
  }

  override def onSessionEnded(request: SessionEndedRequest, session: Session): Unit = {
    logInvocation("onSessionEnded", request, session)
  }
}

object BulbapediaSpeechlet {
  private def logInvocation(name: String, request: SpeechletRequest, session: Session): Unit = {
    val requestId = request.getRequestId
    val sessionId = session.getSessionId
    println(s"$name requestId=$requestId sessionId=$sessionId")
  }
}
