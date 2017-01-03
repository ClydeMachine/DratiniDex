# DratiniDex

![DratiniDex Icon](/src/main/resources/images/DratiniDex-DratiniIconx108.png)

An Alexa skill to retrieve Pokémon evolution information. Written in Scala, utilizing the PokéAPI. Specifically, this skill will tell you a Pokémon's evolutionary stages, and what level that they evolve at. If the evolution requires something other than a level to evolve at, Alexa will give a general response to indicate as much.

Disclaimer: Information is only as up-to-date as the PokeAPI information is. At the time of this writing, Pokémon in Sun/Moon are not available.

# How Do I Use This Alexa Skill?

Ask your Echo or Echo Dot such questions as... 
```
Alexa, ask TiniDex about Bulbasaur.
Alexa, ask TiniDex what level Abra evolves at.
Alexa, ask TiniDex what Venonat evolves into.
Alexa, ask TiniDex how do I use this?
```

At the time of this writing this is not a published skill, so to use it you'll need to build your own. But hey, that's probably why you're on this page anyway, yeah?

# Prerequisites to Build Your Own

The following information assumes you already know how to create Alexa Skills and AWS Lambda functions.

- Java 1.8.x
- Scala 2.11.x
- SBT 0.13.x
- AWS Account for Lambda service access
- An Alexa-capable device, such as the Echo or Echo Dot.

AWS Lambda Configuration Information:
```
Region: us-east-1 (Alexa triggers don't show up in other regions, it seems.)
Runtime: Java 8
Handler: com.clydemachine.dratinidex.DratiniDexSpeechletRequestStreamHandler
Memory: I used 512MB as that's the default. Tweak this if you're adventurous.
```

# License and Legal

I don't own Pokémon, and am not personally affiliated with anyone who maintains Pokémon. I don't own PokéAPI, and am not personally affiliated with anyone who maintains PokéAPI. I do not and will not monetize any part of this software, as I only intend for it to be a useful Alexa skill for those who enjoy the Pokémon games. Feel free to improve, contribute, make your own, etc. according to the license on this project.

# Authors and Notes

Joe Greene <contact@clydemachine.com>
