package com.t3stzer0.jspobfuscator.core.exception;

public class ObfusException extends RuntimeException {

  public ObfusException(String message) {
    super(message);
  }

  public ObfusException(String message, Throwable cause) {
    super(message, cause);
  }
}
