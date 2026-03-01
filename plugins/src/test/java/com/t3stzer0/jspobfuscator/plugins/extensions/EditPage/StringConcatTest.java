package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspPage;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringConcatTest {

  @Test
  void testEditPage() {
    DefaultJspPage page =
        new DefaultJspPage(
            List.of(),
            List.of("String a=\"abcd\"; String b=\"1234\""),
            List.of("String a=\"abcd\"; String b=\"1234\""));
    StringConcat ext = new StringConcat();
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    ext.initExtension(ctx);
    ext.editPage(ctx, page);
    Iterator<PageNode> it = page.getNodes().iterator();
    PageNode node;
    node = it.next();
    assertEquals(
        "String a=\"a\"+\"b\"+\"c\"+\"d\"; String b=\"1\"+\"2\"+\"3\"+\"4\"", node.getText());
    node = it.next();
    assertEquals(
        "String a=\"a\"+\"b\"+\"c\"+\"d\"; String b=\"1\"+\"2\"+\"3\"+\"4\"", node.getText());
    assertFalse(it.hasNext());
  }
}
