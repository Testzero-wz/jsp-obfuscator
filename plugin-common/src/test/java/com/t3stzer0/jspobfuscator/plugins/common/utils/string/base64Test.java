package com.t3stzer0.jspobfuscator.plugins.common.utils.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class base64Test {

  @Test
  void encodeBase64() throws Exception {
    assertEquals("dGVzdA==", base64.EncodeBase64("test"));
  }

  @Test
  void decodeBase64() throws Exception {
    assertArrayEquals("test".getBytes(), base64.DecodeBase64("dGVzdA=="));
  }
}
