package com.example

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testContainsStopKeyword() {
    assertTrue(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("stop"))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("Stop!"))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("stój"))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("zatrzymaj"))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("zatrzymaj się"))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("aurora stop"))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("czekaj"))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("poczekaj"))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("wystarczy"))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("proszę stop"))

    // CRITICAL: Substrings must NOT falsely trigger stop
    assertFalse(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("stopniowo przejdziemy do kolejnego kroku"))
    assertFalse(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("w pewnym stopniu masz rację"))
    assertFalse(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("przystopować"))
    assertFalse(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("dostojny budynek"))
    assertFalse(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("dostępne opcje"))

    // Non-stop queries should not trigger
    assertFalse(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("jaka jest dzisiaj pogoda?"))
    assertFalse(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("opowiedz mi ciekawostkę"))
    assertFalse(com.example.ui.viewmodel.ChatViewModel.containsStopKeyword("kim jesteś"))
  }

  @Test
  fun testIsDirectStopCommand() {
    assertTrue(com.example.ui.viewmodel.ChatViewModel.isDirectStopCommand("stop"))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.isDirectStopCommand("aurora stop"))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.isDirectStopCommand("stój"))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.isDirectStopCommand("zatrzymaj się"))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.isDirectStopCommand("poczekaj chwilę"))

    // Long sentences are not direct stop commands
    assertFalse(com.example.ui.viewmodel.ChatViewModel.isDirectStopCommand("chciałbym abyśmy dzisiaj porozmawiali o tym jak działa funkcja stop w systemach operacyjnych"))
  }

  @Test
  fun testSelfEchoDetection() {
    val aiSpeech = "Warszawa jest stolicą Polski i największym miastem w całym kraju."

    // Recognized text is part of AI's current speech -> should detect as echo
    assertTrue(com.example.ui.viewmodel.ChatViewModel.isSelfEcho("stolicą Polski", aiSpeech))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.isSelfEcho("największym miastem w całym kraju", aiSpeech))
    assertTrue(com.example.ui.viewmodel.ChatViewModel.isSelfEcho("Warszawa jest stolicą", aiSpeech))

    // Recognized text is an independent user query -> should NOT detect as echo
    assertFalse(com.example.ui.viewmodel.ChatViewModel.isSelfEcho("jaka jest pogoda w Krakowie?", aiSpeech))
    assertFalse(com.example.ui.viewmodel.ChatViewModel.isSelfEcho("opowiedz o kosmosie", aiSpeech))
  }
}
