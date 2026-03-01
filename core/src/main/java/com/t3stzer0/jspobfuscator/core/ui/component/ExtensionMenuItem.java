package com.t3stzer0.jspobfuscator.core.ui.component;

import javafx.scene.control.MenuItem;

public class ExtensionMenuItem extends MenuItem {

  protected String fullExtensionKey;
  protected String pluginName;
  protected String lastClassName;
  protected String fullClassName;
  protected String displayName;

  public ExtensionMenuItem(String extKey) {
    this.fullExtensionKey = extKey;
    this.fullClassName = extKey.substring(extKey.indexOf(":") + 1);

    this.lastClassName = extKey.substring(extKey.lastIndexOf(".") + 1);
    this.pluginName = extKey.substring(0, extKey.indexOf(":"));
    this.displayName = this.pluginName + ":" + this.lastClassName;
    super.setText(displayName);
  }

  public String getPluginName() {
    return pluginName;
  }

  public String getFullExtensionKey() {
    return fullExtensionKey;
  }

  public String getLastClassName() {
    return lastClassName;
  }

  public String getFullClassName() {
    return fullClassName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
