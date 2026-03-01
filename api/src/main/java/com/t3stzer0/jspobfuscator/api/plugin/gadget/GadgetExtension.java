package com.t3stzer0.jspobfuscator.api.plugin.gadget;

import com.t3stzer0.jspobfuscator.api.plugin.BaseExtension;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import java.util.List;

public interface GadgetExtension extends BaseExtension {
  List<String> getCode(ExtensionContext ctx);

  List<String> getDeclaration(ExtensionContext ctx);

  List<String> getImports(ExtensionContext ctx);
}
