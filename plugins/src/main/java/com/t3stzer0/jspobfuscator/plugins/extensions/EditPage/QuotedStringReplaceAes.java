package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.DeclarationPageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.EditPageExtension;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.plugins.common.impl.node.DefaultDeclarationPageNode;
import com.t3stzer0.jspobfuscator.plugins.common.impl.node.DefaultImportPageNode;
import com.t3stzer0.jspobfuscator.plugins.common.utils.random.RandomGenerator;
import com.t3stzer0.jspobfuscator.plugins.common.utils.string.aes;
import com.t3stzer0.jspobfuscator.plugins.common.utils.string.replace;
import java.util.List;
import java.util.function.Function;
import org.pf4j.Extension;

@Extension
public class QuotedStringReplaceAes implements EditPageExtension {

  @Override
  public void editPage(ExtensionContext ctx, Page page) {

    RandomGenerator rng = new RandomGenerator(ctx.getRandomSeed());
    // add import
    List<PageNode> nodes = page.getNodes();
    DefaultImportPageNode importNode = new DefaultImportPageNode();
    importNode.addImport("javax.crypto.Cipher");
    importNode.addImport("javax.crypto.spec.SecretKeySpec");
    nodes.add(importNode);

    String key = getRandKey(rng);
    String funcName = getRandFuncName(rng);

    // obfus
    for (PageNode node : nodes) {
      if (node instanceof CodePageNode || node instanceof DeclarationPageNode) {
        node.setText(aesReplaceQuotedString(funcName, key, node.getText()));
      }
    }

    // add declaration
    DefaultDeclarationPageNode declarationNode = new DefaultDeclarationPageNode();
    declarationNode.setText(getAesDecryptFuncDeclaration(funcName, key));
    nodes.add(declarationNode);
  }

  public String getRandFuncName(RandomGenerator rng) {
    int length = rng.nextInt(8, 15);
    StringBuilder name = new StringBuilder();
    for (int i = 0; i < length; i++) {
      name.append(rng.nextChar());
    }
    return name.toString();
  }

  public String getRandKey(RandomGenerator rng) {
    StringBuilder name = new StringBuilder();
    for (int i = 0; i < 16; i++) {
      if (rng.nextBoolean()) {
        name.append(rng.nextChar());
      } else {
        name.append(rng.nextDigitChar());
      }
    }
    return name.toString();
  }

  private String getAesDecryptFuncDeclaration(String funcName, String key) {
    return "public String "
        + funcName
        + "(byte[] e) throws Exception {\n"
        + "\tjavax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(\"AES/ECB/PKCS5Padding\");\n"
        + "\tcipher.init(2, new javax.crypto.spec.SecretKeySpec(\""
        + key
        + "\".getBytes(), \"AES\"));\n"
        + "\treturn new String(cipher.doFinal(e), \"UTF-8\");\n"
        + "}";
  }

  private String getAesEncryptString(String str, String key) {
    byte[] bytes;
    try {
      bytes = aes.aesEncrypt(str.getBytes(), key.getBytes());
    } catch (Exception e) {
      throw new ExtensionException("AES encrypt failed", e);
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      sb.append(String.format("%d", bytes[i]));
      if (i != bytes.length - 1) {
        sb.append(", ");
      }
    }
    return "new byte[]{" + sb + "}";
  }

  private String aesReplaceQuotedString(String funcName, String key, String code) {
    Function<String, String> replaceFunc = s -> funcName + "(" + getAesEncryptString(s, key) + ")";
    return replace.ReplaceQuoteStringWithFunc(code, replaceFunc);
  }
}
