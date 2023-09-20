package org.example.anagram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;
import org.example.anagram.exception.StringValidationException;
import org.example.anagram.service.impl.AnagramProcessorImpl;
import org.example.anagram.service.impl.AnagramWithSortedArrayServiceImpl;
import org.example.anagram.service.impl.StringValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class AnagramProcessorImplTest {

  private final AnagramProcessor anagramProcessor;

  AnagramProcessorImplTest() {
    AnagramService anagramService = new AnagramWithSortedArrayServiceImpl();
    StringValidator stringValidator = new StringValidatorImpl();
    anagramProcessor = new AnagramProcessorImpl(anagramService, stringValidator);
  }

  @ParameterizedTest(name = "{index} | String1: {0} | String2: {1} | Expected result: {2}")
  @MethodSource("provideIsAnagramValidCombinations")
  void isAnagram_isSuccessful_whenValidStringsProvided(String string1, String string2, boolean expected) {
    boolean actual = anagramProcessor.isAnagram(string1, string2);

    assertEquals(expected, actual);
  }

  private static Stream<Arguments> provideIsAnagramValidCombinations() {
    return Stream.of(
        Arguments.of("New York Times", "monkeys write", true),
        Arguments.of("McDonald's restaurants", "Uncle Sam's standard rot", true),
        Arguments.of("mama", "mam", false)
    );
  }

  @ParameterizedTest(name = "{index} | String1: {0} | String2: {1}")
  @MethodSource("provideIsAnagramInvalidCombinations")
  void isAnagram_throwsStringValidationException_whenInvalidStringsProvided(String string1, String string2) {
    StringValidationException exception = assertThrows(StringValidationException.class,
        () -> anagramProcessor.isAnagram(string1, string2));

    assertTrue(exception.getMessage().contains("is not valid"));
  }

  private static Stream<Arguments> provideIsAnagramInvalidCombinations() {
    return Stream.of(
        Arguments.of(" ", "monkeys write"),
        Arguments.of(null, "Uncle Sam's standard rot"),
        Arguments.of("mama", "")
    );
  }

  @Test
  void getAnagrams_isSuccessful_whenValidStringProvided() {
    String stringA = "mama";
    String stringB = "maam";
    String stringC = "papa";
    String stringD = "amma";

    anagramProcessor.isAnagram(stringA, stringB);
    anagramProcessor.isAnagram(stringA, stringC);
    anagramProcessor.isAnagram(stringA, stringD);

    assertEquals(List.of(stringB, stringD), anagramProcessor.getAnagrams(stringA));
    assertEquals(List.of(stringA, stringD), anagramProcessor.getAnagrams(stringB));
    assertTrue(anagramProcessor.getAnagrams(stringC).isEmpty());
  }

  @ParameterizedTest()
  @ValueSource(strings = {"", "    ", "._?"})
  void getAnagrams_throwsStringValidationException_whenInvalidStringProvided(String string) {
    StringValidationException exception = assertThrows(StringValidationException.class,
        () -> anagramProcessor.getAnagrams(string));

    assertTrue(exception.getMessage().contains("is not valid"));
  }
}
