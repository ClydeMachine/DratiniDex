package com.clydemachine.alexabulbapedia.speech.speechlet

import com.amazon.speech.speechlet
import com.amazon.speech.ui.{Card, OutputSpeech, Reprompt}

class SpeechletResponse(
                       outputSpeech: OutputSpeech = null,
                       card: Card = null,
                       reprompt: Reprompt = null,
                       shouldEndSession: Boolean = true
                       ) extends speechlet.SpeechletResponse {
  setOutputSpeech(outputSpeech)
  setCard(card)
  setReprompt(reprompt)
  setShouldEndSession(shouldEndSession)
}
