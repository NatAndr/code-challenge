package org.example.anagram;

import org.example.anagram.service.InteractionService;
import org.example.anagram.service.impl.InteractionServiceImpl;

public class App {

  public static void main(String[] args) {
    InteractionService interactionService = new InteractionServiceImpl();
    interactionService.run();
  }
}
