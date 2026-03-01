package com.t3stzer0.jspobfuscator.core.ui.component;

public class ExtensionTableViewItem {

  private final String uuid;
  private final String extensionKey;
  private final String displayName;

  public ExtensionTableViewItem(String uuid, String extensionKey) {
    this.uuid = uuid;
    this.extensionKey = extensionKey;
    this.displayName = buildDisplayName(extensionKey);
  }

  public String buildDisplayName(String extKey) {
    String className = extKey.substring(extKey.lastIndexOf(".") + 1);
    String pluginName = extKey.substring(0, extKey.indexOf(":") + 1);
    return pluginName + className;
  }

  public String getUuid() {
    return uuid;
  }

  public String getExtensionKey() {
    return extensionKey;
  }

  public String getDisplayName() {
    return displayName;
  }
}
