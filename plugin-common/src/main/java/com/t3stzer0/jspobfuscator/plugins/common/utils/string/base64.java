package com.t3stzer0.jspobfuscator.plugins.common.utils.string;

import java.util.Base64;

public class base64 {
  public static String EncodeBase64(String str) throws Exception {
    return Base64.getEncoder().encodeToString(str.getBytes());
  }

  public static byte[] DecodeBase64(String str) throws Exception {
    return Base64.getDecoder().decode(str);
  }
}
