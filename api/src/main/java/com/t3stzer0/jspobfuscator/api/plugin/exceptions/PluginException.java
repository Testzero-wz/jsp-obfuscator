package com.t3stzer0.jspobfuscator.api.plugin.exceptions;

public class PluginException extends RuntimeException {
  public PluginException(String message) {
    super(message);
  }

  public PluginException(String message, Throwable cause) {
    super(message, cause);
  }
}
