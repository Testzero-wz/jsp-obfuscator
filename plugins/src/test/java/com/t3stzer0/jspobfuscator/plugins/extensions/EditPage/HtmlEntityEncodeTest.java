package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspxPage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HtmlEntityEncodeTest {

  @Test
  void editPage() {
    List<String> codes = List.of("abc");
    DefaultJspxPage page = new DefaultJspxPage(List.of(), List.of(), codes);
    HtmlEntityEncode ext = new HtmlEntityEncode();
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    ext.initExtension(ctx);
    ctx.setSelectInput("radix", 0);
    ext.editPage(ctx, page);
    PageNode node = page.getNodes().stream().filter(n -> n instanceof CodePageNode).findFirst().get();
    assertNotNull(node);
    String text = node.getText();
    assertEquals("&#x61;&#x00062;&#x0000000063;", text);
  }
}
