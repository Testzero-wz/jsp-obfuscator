package com.t3stzer0.jspobfuscator.core.domain.extension;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import java.util.Map;

public class ExtensionRuntimeContext extends ExtensionContextAdapter {

  public ExtensionRuntimeContext() {
    seed = System.currentTimeMillis();
  }

  public Map<String, ExtensionInputItem> getInputMap() {
    return input;
  }
}
