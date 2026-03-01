package com.t3stzer0.jspobfuscator.core.domain.extension;

import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;

public class ExtensionExceptionWrapper {
  public static ExtensionException wrapFromExtensionException(
      ExtensionBinding extBinding, ExtensionException e) {
    return new ExtensionException(extBinding.getKey() + ": " + e.getMessage(), e);
  }
}
