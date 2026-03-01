package com.t3stzer0.jspobfuscator.plugins.common.utils.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class htmlEntityTest {

    @Test
    void encodeHex() {
        assertEquals("&#x00061;", htmlEntity.encodeHex('a', 3));
      }

    @Test
    void encodeDecimal() {
        assertEquals("&#00097;", htmlEntity.encodeDecimal('a', 3));
      }
}