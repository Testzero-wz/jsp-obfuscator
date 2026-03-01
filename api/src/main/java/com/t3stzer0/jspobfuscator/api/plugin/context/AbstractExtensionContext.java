package com.t3stzer0.jspobfuscator.api.plugin.context;

import java.util.Map;

public abstract class AbstractExtensionContext implements ExtensionContext {
  protected final Map<String, Object> pluginParams = new java.util.HashMap<>();

  public AbstractExtensionContext() {}

  @Override
  public Object getCtxParam(String key) {
    return pluginParams.get(key);
  }

  @Override
  public void setCtxParam(String key, Object value) {
    pluginParams.put(key, value);
  }
}
