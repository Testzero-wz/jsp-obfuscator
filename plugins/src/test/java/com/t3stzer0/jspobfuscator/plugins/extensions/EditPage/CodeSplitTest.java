package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import static org.junit.jupiter.api.Assertions.*;

import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspPage;
import java.util.List;
import org.junit.jupiter.api.Test;

class CodeSplitTest {

  @Test
  void editPage() {
    List<String> codes = List.of("""
int a=1; 

String b=\"2\";;;;;;


float c=1.0;
""");
    DefaultJspPage page = new DefaultJspPage(List.of(), List.of(), codes);
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    CodeSplit codeSplit = new CodeSplit();
    codeSplit.editPage(ctx, page);
    List<PageNode> codePageNodes =
        page.getNodes().stream().filter(node -> node instanceof CodePageNode).toList();
    assertEquals(3, codePageNodes.size());
    PageNode node0 = codePageNodes.get(0);
    assertEquals("int a=1;", node0.getText());
    PageNode node1 = codePageNodes.get(1);
    assertEquals("String b=\"2\";;;;;;", node1.getText());
    PageNode node2 = codePageNodes.get(2);
    assertEquals("float c=1.0;", node2.getText());
  }
}
