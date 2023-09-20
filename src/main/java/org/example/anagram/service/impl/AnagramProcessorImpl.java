package org.example.anagram.service.impl;

import org.example.anagram.exception.StringValidationException;
import org.example.anagram.service.AnagramProcessor;
import org.example.anagram.service.AnagramService;
import org.example.anagram.service.StringValidator;
import org.example.anagram.util.StringUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AnagramProcessorImpl implements AnagramProcessor {

    private static final String STRING_IS_NOT_VALID_ERROR_MESSAGE_TEMPLATE = "String '%s' is not valid";

    private final AnagramService anagramService;
    private final StringValidator stringValidator;
    private final Map<String, Set<String>> anagramsMap;

    public AnagramProcessorImpl(AnagramService anagramService, StringValidator stringValidator) {
        this.anagramService = anagramService;
        this.stringValidator = stringValidator;
        anagramsMap = new HashMap<>();
    }

    @Override
    public boolean isAnagram(String str1, String str2) {
        Stream.of(str1, str2).forEach(this::validateString);

        String string1 = StringUtil.prepare(str1);
        String string2 = StringUtil.prepare(str2);

        boolean isAnagram = anagramService.isAnagram(string1, string2);

        if (!isAnagram) {
            return false;
        }

        String key = getSortedKey(string1);

        Stream.of(string1, string2)
            .forEach(str -> anagramsMap.computeIfAbsent(key, k -> new HashSet<>()).add(str));

        return true;
    }

    @Override
    public List<String> getAnagrams(String str) {
        validateString(str);

        String string = StringUtil.prepare(str);
        String key = getSortedKey(string);

        return anagramsMap.getOrDefault(key, Collections.emptySet()).stream()
            .filter(s -> !s.equalsIgnoreCase(string))
            .collect(Collectors.toList());
    }

    private void validateString(String string) {
        if (!stringValidator.isValid(string)) {
            throw new StringValidationException(String.format(STRING_IS_NOT_VALID_ERROR_MESSAGE_TEMPLATE, string));
        }
    }

    private String getSortedKey(String string) {
        char[] c = string.toCharArray();
        Arrays.sort(c);

        return new String(c);
    }
}
