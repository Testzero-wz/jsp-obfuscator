package com.t3stzer0.jspobfuscator.plugins.common.exception;

import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;

public class RenderError extends ExtensionException {
  public RenderError(String message) {
    super(message);
  }

  public RenderError(String message, Throwable cause) {
    super(message, cause);
  }
}
