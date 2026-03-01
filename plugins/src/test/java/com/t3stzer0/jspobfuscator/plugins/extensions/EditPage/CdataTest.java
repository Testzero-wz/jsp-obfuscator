package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import static org.junit.jupiter.api.Assertions.*;

import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspxPage;
import java.util.List;
import org.junit.jupiter.api.Test;

class CdataTest {

  @Test
  void editPage() {
    List<String> codes = List.of("a b c");
    DefaultJspxPage page = new DefaultJspxPage(List.of(), List.of(), codes);
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    Cdata ext = new Cdata();
    ext.editPage(ctx, page);
    List<PageNode> codePageNodes =
        page.getNodes().stream().filter(node -> node instanceof CodePageNode).toList();
    assertEquals(codes.size(), codePageNodes.size());
    PageNode node = codePageNodes.get(0);
    assertEquals(
        "<![CDATA[a]]><![CDATA[ ]]><![CDATA[b]]><![CDATA[ ]]><![CDATA[c]]>", node.getText());
  }
}
