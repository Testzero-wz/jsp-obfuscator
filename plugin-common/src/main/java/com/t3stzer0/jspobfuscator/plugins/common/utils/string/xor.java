package com.t3stzer0.jspobfuscator.plugins.common.utils.string;

public class xor {
  public static byte[] xorEncrypt(byte[] bytes, byte[] key) {
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) ((bytes[i]) ^ (key[i % key.length]));
    }
    return bytes;
  }

  public static byte[] xorDecrypt(byte[] bytes, byte[] key) {
    return xorEncrypt(bytes, key);
  }
}
