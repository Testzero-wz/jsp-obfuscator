package com.t3stzer0.jspobfuscator.plugins.common.utils.random;

import java.util.Random;

public class RandomGenerator extends Random {

  static final char[] alphabet =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  static final char[] digits = "0123456789".toCharArray();

  public RandomGenerator() {
    // use time as seed
    super(System.currentTimeMillis());
  }

  public RandomGenerator(long seed) {
    super(seed);
  }

  public char nextChar() {
    return alphabet[this.nextInt(alphabet.length)];
  }

  public char nextDigitChar() {
    return digits[this.nextInt(digits.length)];
  }
}
