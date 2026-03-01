package com.t3stzer0.jspobfuscator.plugins.common.utils.string;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class aes {

  public static byte[] aesEncrypt(byte[] plainText, byte[] secretKey)
      throws InvalidKeyException,
          IllegalBlockSizeException,
          BadPaddingException,
          NoSuchPaddingException,
          NoSuchAlgorithmException {
    SecretKeySpec key = new SecretKeySpec(secretKey, "AES");
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, key);
    return cipher.doFinal(plainText);
  }

  public static byte[] aesDecrypt(byte[] cipherText, byte[] secretKey)
      throws NoSuchPaddingException,
          NoSuchAlgorithmException,
          InvalidKeyException,
          IllegalBlockSizeException,
          BadPaddingException {
    SecretKeySpec key = new SecretKeySpec(secretKey, "AES");
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, key);
    return cipher.doFinal(cipherText);
  }
}
