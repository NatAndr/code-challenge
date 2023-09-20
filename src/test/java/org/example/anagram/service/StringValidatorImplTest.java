package org.example.anagram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.example.anagram.service.impl.StringValidatorImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StringValidatorImplTest {

  private final StringValidator stringValidator = new StringValidatorImpl();

  @ParameterizedTest(name = "{index} | String: {0} | Expected result: {1}")
  @MethodSource("provideIsValidCombinations")
  void isValid_isSuccessful(String string, boolean expected) {
    boolean actual = stringValidator.isValid(string);

    assertEquals(expected, actual);
  }

  private static Stream<Arguments> provideIsValidCombinations() {
    return Stream.of(
        Arguments.of("New York Times", true),
        Arguments.of("McDonald's restaurants", true),
        Arguments.of(null, false),
        Arguments.of(" ", false),
        Arguments.of(".", false)
    );
  }
}
