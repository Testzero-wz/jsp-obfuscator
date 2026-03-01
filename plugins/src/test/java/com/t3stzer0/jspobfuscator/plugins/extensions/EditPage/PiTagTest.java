package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspxPage;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PiTagTest {

  @Test
  void testPiTag() {
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    PiTag  ext= new PiTag();
    List<String> code =
        List.of(
"""
String test = "test";
""");
    DefaultJspxPage page = new DefaultJspxPage(List.of(), List.of(), code);
    ext.editPage(ctx, page);
    PageNode node;
    Iterator<PageNode> it = page.getNodes().iterator();
    // skip xml prolog and root node
    it.next();
    it.next();
    node = it.next();
    assertTrue(node instanceof CodePageNode);
    String expected = """
S<?Ah ?>t<?Wm ?>r<?Ar ?>i<?nQ ?>n<?dP ?>g<?AA ?> <?iG ?>t<?ue ?>e<?WI ?>s<?lz ?>t<?OR ?> <?Ar ?>=<?Zv ?> <?Mg ?>"<?tY ?>t<?MK ?>e<?sH ?>s<?HV ?>t<?GL ?>"<?PK ?>;<?Ff ?>
""";
    assertEquals(expected, node.getText());
  }
}
