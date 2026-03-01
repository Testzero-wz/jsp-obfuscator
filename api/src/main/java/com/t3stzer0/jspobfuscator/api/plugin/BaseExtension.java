package com.t3stzer0.jspobfuscator.api.plugin;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.context.InitExtensionContext;
import org.pf4j.ExtensionPoint;

public interface BaseExtension extends ExtensionPoint {
  default void initExtension(ExtensionContext ctx) {}

  default String getHelp() {
    return "";
  }
}
