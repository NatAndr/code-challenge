package org.example.anagram.service.impl;


import org.example.anagram.service.StringValidator;
import org.example.anagram.util.StringUtil;

public class StringValidatorImpl implements StringValidator {

  @Override
  public boolean isValid(String string) {
    return string != null && !string.isEmpty() && !string.isBlank() && !StringUtil.prepare(string).isEmpty();
  }
}
