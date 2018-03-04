package com.nfitton.demostructure.util.generator;

import java.util.Random;

public class StringGenerator {

  private static Random random = new Random();

  public static String generateName() {
    return generateString(13);
  }

  public static String generateEmail() {
    return generateString(5) + '@' + generateString(7) + '.' + generateString(3);
  }

  public static String generatePassword() {
    return generateString(25);
  }

  private static String generateString(int length) {
    StringBuilder builder = new StringBuilder();
    random.ints(0, 26).limit(length).forEach(i -> builder.append((char) (i + (int) 'a')));
    return builder.toString();
  }
}
