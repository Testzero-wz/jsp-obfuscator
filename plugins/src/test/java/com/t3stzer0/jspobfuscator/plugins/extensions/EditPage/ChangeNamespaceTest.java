package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspPage;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspxPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeNamespaceTest {

  @Test
  void testDefaultNamespace() {
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    ChangeNamespace ext = new ChangeNamespace();
    ext.initExtension(ctx);
    ctx.setStringInput("namespace", null);
    DefaultJspPage page = new DefaultJspPage();
    try {
      ext.editPage(ctx, page);
      throw new RuntimeException("Expected Exception");
    } catch (ExtensionException e) {
      // should go into this branch
    }

    DefaultJspxPage pagex = new DefaultJspxPage();
    ext.editPage(ctx, pagex);
    assertEquals("jsp", pagex.getNamespace());
  }

  @Test
  void testChangeNamespace() {
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    String namespace = "中文命名空间";
    ChangeNamespace ext = new ChangeNamespace();
    DefaultJspxPage page = new DefaultJspxPage();
    ext.initExtension(ctx);
    ctx.setStringInput("namespace", namespace);
    ext.editPage(ctx, page);
    assertEquals(namespace, page.getNamespace());
  }

}
