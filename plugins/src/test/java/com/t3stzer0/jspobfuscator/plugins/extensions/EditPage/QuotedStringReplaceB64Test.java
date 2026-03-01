package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.DeclarationPageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import com.t3stzer0.jspobfuscator.plugins.common.impl.node.DefaultImportPageNode;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspPage;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuotedStringReplaceB64Test {

  @Test
  void testQuotedStringReplaceB64() {
    List<String> code =
        List.of(
"""
String a = "AA";
""");
    DefaultJspPage page = new DefaultJspPage(List.of(), List.of(), code);
    QuotedStringReplaceB64 ext = new QuotedStringReplaceB64();
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    ext.editPage(ctx, page);
    assertEquals(1, page.getNodes().size());
    PageNode node;
    Iterator<PageNode> iterator = page.getNodes().iterator();
    node = iterator.next();
    assertTrue(node instanceof CodePageNode);
    String expected =
"""
String a = new String(java.util.Base64.getDecoder().decode("QUE="));
""";
    assertEquals(expected, node.getText());
  }
}
