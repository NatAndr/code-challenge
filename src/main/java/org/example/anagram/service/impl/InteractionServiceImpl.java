package org.example.anagram.service.impl;

import org.example.anagram.service.AnagramProcessor;
import org.example.anagram.service.AnagramService;
import org.example.anagram.service.InteractionService;
import org.example.anagram.service.StringValidator;

import java.util.List;
import java.util.Scanner;

public class InteractionServiceImpl implements InteractionService {

    private static final String INITIAL_PROMPT_MESSAGE = """
        Enter feature number:\s
         1 - check if two strings are anagrams of each other\s
         2 - list all the anagrams for a given string out of all inputs to feature #1\s
         3 - exit""";

    private static final String STRING_PROMPT_MESSAGE_TEMPLATE = "Enter string %s:";
    private static final String STRING_PROMPT_MESSAGE = "Enter string:";
    private static final String FEATURE_ANAGRAM_CHECK = "1";
    private static final String FEATURE_LIST_ANAGRAMS = "2";
    private static final String EXIT = "3";
    private static final String EXIT_MESSAGE = "Bye!";
    private static final String WRONG_INPUT_MEASGE = "Please enter correct number.";
    private static final String ANAGRAM_CHECK_RESULT_MESSAGE = "These two strings are %sanagrams%n";
    private static final String NO_ANAGRAMS_MESSAGE = "no anagrams for a given string so far";
    private static final String LIST_ANAGRAMS_MESSAGE = "All anagrams for a given string: ";
    private static final String EMPTY = "";
    private static final String NOT = "not ";

    private final AnagramProcessor anagramProcessor;

    public InteractionServiceImpl() {
        AnagramService anagramService = new AnagramWithSortedArrayServiceImpl();
        StringValidator stringValidator = new StringValidatorImpl();
        this.anagramProcessor = new AnagramProcessorImpl(anagramService, stringValidator);
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            String input = "";
            while (!EXIT.equals(input)) {
                input = readString(INITIAL_PROMPT_MESSAGE, scanner);
                switch (input) {
                    case FEATURE_ANAGRAM_CHECK -> {
                        String string1 = readString(String.format(STRING_PROMPT_MESSAGE_TEMPLATE, 1), scanner);
                        String string2 = readString(String.format(STRING_PROMPT_MESSAGE_TEMPLATE, 2), scanner);
                        boolean result = anagramProcessor.isAnagram(string1, string2);
                        System.out.printf(ANAGRAM_CHECK_RESULT_MESSAGE, result ? EMPTY : NOT);
                    }
                    case FEATURE_LIST_ANAGRAMS -> {
                        String string = readString(STRING_PROMPT_MESSAGE, scanner);
                        List<String> anagrams = anagramProcessor.getAnagrams(string);
                        System.out.println(anagrams.isEmpty() ? NO_ANAGRAMS_MESSAGE : LIST_ANAGRAMS_MESSAGE + anagrams);
                    }
                    case EXIT -> System.out.println(EXIT_MESSAGE);
                    default -> System.out.println(WRONG_INPUT_MEASGE);
                }
            }
        }
    }

    private String readString(String prompt, Scanner scanner) {
        System.out.println(prompt);
        return scanner.next();
    }
}
