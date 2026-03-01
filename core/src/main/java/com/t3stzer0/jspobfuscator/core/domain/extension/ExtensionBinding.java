package com.t3stzer0.jspobfuscator.core.domain.extension;

import com.t3stzer0.jspobfuscator.api.plugin.BaseExtension;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.core.service.PluginService;

public class ExtensionBinding {
  private final ExtensionContext extCtx;
  private final BaseExtension ext;
  private final String extKey;

  public ExtensionBinding(String extKey, PluginService pluginService) {
    this.extKey = extKey;
    this.ext = pluginService.getExtension(extKey);
    this.extCtx = new ExtensionRuntimeContext();
    ext.initExtension(extCtx);
  }

  public BaseExtension getExtension() {
    return ext;
  }

  public ExtensionContext getExtensionCtx() {
    return extCtx;
  }

  public String getKey() {
    return extKey;
  }
}
