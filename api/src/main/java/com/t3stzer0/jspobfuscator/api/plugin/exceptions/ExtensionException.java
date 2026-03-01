package com.t3stzer0.jspobfuscator.api.plugin.exceptions;

public class ExtensionException extends PluginException {
  public ExtensionException(String message) {
    super(message);
  }

  public ExtensionException(String message, Throwable cause) {
    super(message, cause);
  }
}
