package org.example.anagram.service.impl;

import java.util.Arrays;
import org.example.anagram.service.AnagramService;

public class AnagramWithSortedArrayServiceImpl implements AnagramService {

  @Override
  public boolean isAnagram(String string1, String string2) {
    if (string1.length() != string2.length()) {
      return false;
    }

    char[] c1 = string1.toCharArray();
    Arrays.sort(c1);

    char[] c2 = string2.toCharArray();
    Arrays.sort(c2);

    String s1 = new String(c1);
    String t1 = new String(c2);

    return s1.equals(t1);
  }
}
