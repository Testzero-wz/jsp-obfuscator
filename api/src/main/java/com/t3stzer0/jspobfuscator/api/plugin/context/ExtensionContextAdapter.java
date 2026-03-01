package com.t3stzer0.jspobfuscator.api.plugin.context;

import java.util.List;
import java.util.Map;

public class ExtensionContextAdapter extends AbstractExtensionContext {
  protected long seed = 42;
  protected Map<String, ExtensionInputItem> input = new java.util.HashMap<>();

  public static class ExtensionInputItem {
    boolean required;
    InputType type;
    List<String> ops;
    Integer selectedIndex = null;
    String stringValue = null;
    Float numberValue = null;

    public ExtensionInputItem(InputType type, boolean required) {
      this.type = type;
      this.required = required;
    }

    public List<String> getOptions() {
      return ops;
    }

    public InputType getType() {
      return type;
    }

    public boolean isRequired() {
      return required;
    }

    public String getStringValue() {
      if (type != InputType.STRING) {
        throw new IllegalStateException(
            "getStringValue() can only be used when type == STRING, but current type is: " + type);
      }
      return stringValue;
    }

    public Float getNumberValue() {
      if (type != InputType.NUMBER) {
        throw new IllegalStateException(
            "getNumberValue() can only be used when type == NUMBER, but current type is: " + type);
      }
      return numberValue;
    }

    public Integer getSelectValue() {
      if (type != InputType.SELECT) {
        throw new IllegalStateException(
            "getSelectValue() can only be used when type == SELECT, but current type is: " + type);
      }
      return selectedIndex;
    }
  }

  public enum InputType {
    STRING,
    NUMBER,
    SELECT
  }

  @Override
  public long getRandomSeed() {
    return seed;
  }

  @Override
  public void registerStringInput(String name, boolean required, String defaultValue) {
    ExtensionInputItem item = new ExtensionInputItem(InputType.STRING, required);
    item.stringValue = defaultValue;
    input.put(name, item);
  }

  @Override
  public void registerFloatInput(String name, boolean required, Float defaultValue) {
    ExtensionInputItem item = new ExtensionInputItem(InputType.NUMBER, required);
    item.numberValue = defaultValue;
    input.put(name, item);
  }

  @Override
  public void registerSelectInput(
      String name, List<String> options, boolean required, Integer defaultValue) {
    ExtensionInputItem item = new ExtensionInputItem(InputType.SELECT, required);
    item.ops = options;
    item.selectedIndex = defaultValue;
    input.put(name, item);
  }

  public void setStringInput(String name, String value) {
    if (!input.containsKey(name)) {
      throw new IllegalArgumentException(name + " not found");
    }
    input.get(name).stringValue = value;
  }

  public void setNumberInput(String name, Float value) {
    if (!input.containsKey(name)) {
      throw new IllegalArgumentException(name + " not found");
    }
    input.get(name).numberValue = value;
  }

  public void setSelectInput(String name, Integer index) {
    if (!input.containsKey(name)) {
      throw new IllegalArgumentException(name + " not found");
    }
    input.get(name).selectedIndex = index;
  }

  @Override
  public String getStringInput(String key) {
    if (!input.containsKey(key)) {
      throw new IllegalArgumentException(key + " not found");
    }
    if (input.get(key).type != InputType.STRING) {
      throw new IllegalArgumentException(key + " is not a string input");
    }
    return input.get(key).stringValue;
  }

  @Override
  public float getFloatInput(String key) {
    if (!input.containsKey(key)) {
      throw new IllegalArgumentException(key + " not found");
    }
    if (input.get(key).type != InputType.NUMBER) {
      throw new IllegalArgumentException(key + " is not a number input");
    }
    return input.get(key).numberValue;
  }

  @Override
  public Integer getSelectInput(String key) {
    if (!input.containsKey(key)) {
      throw new IllegalArgumentException(key + " not found");
    }
    if (input.get(key).type != InputType.SELECT) {
      throw new IllegalArgumentException(key + " is not a select input");
    }
    return input.get(key).selectedIndex;
  }
}
