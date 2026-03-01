package com.t3stzer0.jspobfuscator.api.plugin.gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import java.util.List;

public abstract class AbstructGadgetExtension implements GadgetExtension {

  @Override
  public List<String> getCode(ExtensionContext ctx) {
    return List.of();
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
