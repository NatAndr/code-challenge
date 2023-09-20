package org.example.anagram.service;

import java.util.List;

public interface AnagramProcessor {

  boolean isAnagram(String string1, String string2);

  List<String> getAnagrams(String string);
}
