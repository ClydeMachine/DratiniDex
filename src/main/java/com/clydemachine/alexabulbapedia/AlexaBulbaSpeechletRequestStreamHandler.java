package com.clydemachine.alexabulbapedia;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import com.clydemachine.alexabulbapedia.AlexaBulbaSpeechlet;

import java.util.HashSet;
import java.util.Set;

public class AlexaBulbaSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
  private static final Set<String> appIds = new HashSet<String>();
  static {
    //Add new Application IDs that should be allowed to use this skill with: appIds.add("");
    appIds.add("amzn1.ask.skill.38b4a620-6c5d-4aed-aec0-7ad1831cb18c");
  }

  public AlexaBulbaSpeechletRequestStreamHandler() {
    super( new AlexaBulbaSpeechlet(), appIds );
  }
}
