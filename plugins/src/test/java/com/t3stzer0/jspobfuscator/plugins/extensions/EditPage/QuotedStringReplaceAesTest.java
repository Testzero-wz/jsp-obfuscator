package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspPage;

import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class QuotedStringReplaceAesTest {
  @Test
  public void testAesStringReplace() {
    List<String> imports = List.of();
    List<String> declarations = List.of();
    List<String> codes = List.of("String quotedString = \"xxx\";");
    DefaultJspPage page = new DefaultJspPage(imports, declarations, codes);
    ExtensionContext ctx = new ExtensionContextAdapter();
    QuotedStringReplaceAes quotedStringReplaceAes = new QuotedStringReplaceAes();
    quotedStringReplaceAes.editPage(ctx, page);
    Iterator<PageNode> it = page.getNodes().iterator();
    PageNode node;
    node = it.next();
    assertTrue(node instanceof CodePageNode);
    String expected;
    expected = "String quotedString = HHVGLPKFfV(new byte[]{-118, 60, 23, 73, -95, -39, 71, -7, 51, 75, -11, -15, 117, -73, -76, 57});";

    assertEquals(expected, node.getText());
  }

}
