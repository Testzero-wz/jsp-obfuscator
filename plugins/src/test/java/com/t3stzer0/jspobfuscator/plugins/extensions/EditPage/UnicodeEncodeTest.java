package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.plugins.common.utils.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnicodeEncodeTest {

  @Test
  void unicodeEncode() {
    UnicodeEncode ext = new UnicodeEncode();
    assertEquals(
        "\\uuuuuuu0048\\uuuuu0065\\uuuuuuuuu006C\\uuuuuuuuu006C\\uuuuuuu006f",
        ext.unicodeEncode("Hello", new RandomGenerator(0)));
  }
}
