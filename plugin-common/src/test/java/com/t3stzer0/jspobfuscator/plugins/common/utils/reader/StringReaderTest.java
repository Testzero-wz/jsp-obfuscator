package com.t3stzer0.jspobfuscator.plugins.common.utils.reader;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class StringReaderTest {

  @Test
  void testReadUntilOrEnd() {
    StringReader reader = new StringReader("aaa'bbb'ccc'ddd");
    char sep = '\'';
    String result;
    result = reader.readUntilOrEnd(sep);
    assertEquals("aaa", result);
    assertFalse(reader.isReachEOF());
    assertEquals(1, reader.skip(1));

    result = reader.readUntilOrEnd(sep);
    assertEquals("bbb", result);
    assertFalse(reader.isReachEOF());
    assertEquals(1, reader.skip(1));

    result = reader.readUntilOrEnd(sep);
    assertEquals("ccc", result);
    assertFalse(reader.isReachEOF());
    assertEquals(1, reader.skip(1));

    result = reader.readUntilOrEnd(sep);
    assertEquals("ddd", result);
    assertTrue(reader.isReachEOF());
    assertEquals(0, reader.skip(1));
  }

  @Test
  void testPeekOne() {
    StringReader reader = new StringReader("abc");
    assertEquals('a', reader.peekOne());
    assertEquals(1, reader.skip(1));
    assertEquals('b', reader.peekOne());
    assertEquals(1, reader.skip(1));
    assertEquals('c', reader.peekOne());
    assertEquals(1, reader.skip(1));
    assertTrue(reader.isReachEOF());
    assertEquals(-1, reader.peekOne());
  }
}
