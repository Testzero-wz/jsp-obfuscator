package com.t3stzer0.jspobfuscator.plugins.common.utils.string;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class xorTest {

  @Test
  void testXorEncrypt() {
    byte[] bytes = "hello world".getBytes();
    byte[] key = "1234".getBytes();
    byte[] encrypted = xor.xorEncrypt(bytes, key);
    byte[] expected = {0x59, 0x57, 0x5f, 0x58, 0x5e, 0x12, 0x44, 0x5b, 0x43, 0x5e, 0x57};
    assertArrayEquals(encrypted, expected);
    assertArrayEquals(bytes, xor.xorDecrypt(encrypted, key));
  }
}
