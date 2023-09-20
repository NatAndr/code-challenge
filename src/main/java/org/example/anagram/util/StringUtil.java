package org.example.anagram.util;

public class StringUtil {

  private StringUtil() {
  }

  public static String prepare(String string) {
    return string.replaceAll("[^a-zA-Z0-9]", "").trim().toLowerCase();
  }
}
