package com.t3stzer0.jspobfuscator.api.plugin.context;

import java.util.List;

public interface ExtensionContext {

  long getRandomSeed();

  // context
  Object getCtxParam(String key);

  void setCtxParam(String key, Object value);

  // ui interface
  void registerStringInput(String name, boolean required, String defaultValue);

  void registerFloatInput(String name, boolean required, Float defaultValue);

  void registerSelectInput(
      String name, List<String> options, boolean required, Integer defaultValue);

  String getStringInput(String key);

  float getFloatInput(String key);

  Integer getSelectInput(String key);
}
