package com.t3stzer0.jspobfuscator.plugins.common.utils.string;

import java.util.function.Function;
import java.util.regex.MatchResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class replaceTest {

  @Test
  void testReplaceWithFunc() {
    String data = "aaaxxxxxxbbbxxxxxxxxxcccxxxxx";
    Function<MatchResult, String> func = mr -> "<replaced>";
    String expected = "aaa<replaced>bbb<replaced>ccc<replaced>";
    assertEquals(expected, replace.ReplaceWithFunc(data, "x+", func));
  }

  @Test
  void testReplaceQuoteStringWithFunc() {
    String data = "String a = \"xxxxx\"; String b = \"xxxx\"; char c = 'x'";
    Function<String, String> func = mr -> "f(x)";
    String expected = "String a = f(x); String b = f(x); char c = 'x'";
    assertEquals(expected, replace.ReplaceQuoteStringWithFunc(data, func));
  }
}
