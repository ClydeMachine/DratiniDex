package com.clydemachine.alexabulbapedia.speech.speechlet

import com.amazon.speech.speechlet
import com.amazon.speech.ui.{Card, OutputSpeech, Reprompt}
import com.clydemachine.alexabulbapedia.speech.ui.PlainTextOutputSpeech

class SpeechletResponse(
                       outputSpeech: OutputSpeech = null,
                       repromptText: String = "",
                       card: Card = null,
                       shouldEndSession: Boolean = true
                       ) extends speechlet.SpeechletResponse {

  // Handle reprompt text, convert to Reprompt type before passing.
  var repromptOutputSpeech: OutputSpeech = new PlainTextOutputSpeech(repromptText)
  var reprompt: Reprompt = new Reprompt
  reprompt.setOutputSpeech(repromptOutputSpeech)

  setOutputSpeech(outputSpeech)
  setCard(card)
  setReprompt(reprompt)
  setShouldEndSession(shouldEndSession)
}
