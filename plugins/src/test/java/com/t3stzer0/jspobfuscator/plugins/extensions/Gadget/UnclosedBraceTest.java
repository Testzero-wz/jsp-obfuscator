package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnclosedBraceTest {

  @Test
  void testGetCode() {
    UnclosedBrace ext  = new UnclosedBrace();
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    ext.initExtension(ctx);
    List<String> code = ext.getCode(ctx);
    assertEquals(2, code.size());
  }
}
