package com.clydemachine.dratinidex;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import com.clydemachine.dratinidex.DratiniDexSpeechlet;

import java.util.HashSet;
import java.util.Set;

public class DratiniDexSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
  private static final Set<String> appIds = new HashSet<String>();
  static {
    //Add new Application IDs that should be allowed to use this skill with: appIds.add("");
    appIds.add("amzn1.ask.skill.d2bfbe87-e22d-4cff-b9fe-b06c31120a49");
    appIds.add("amzn1.ask.skill.38b4a620-6c5d-4aed-aec0-7ad1831cb18c");
  }

  public DratiniDexSpeechletRequestStreamHandler() {
    super( new DratiniDexSpeechlet(), appIds );
  }
}
