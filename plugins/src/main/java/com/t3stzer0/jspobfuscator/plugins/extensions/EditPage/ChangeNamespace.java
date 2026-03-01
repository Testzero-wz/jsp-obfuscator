package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.EditPageExtension;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspxPage;
import org.pf4j.Extension;


@Extension
public class ChangeNamespace implements EditPageExtension {

  @Override
  public void initExtension(ExtensionContext ctx) {
    ctx.registerStringInput("namespace", true, "jsp");
  }

  @Override
  public void editPage(ExtensionContext ctx, Page page) {
    if (!(page instanceof DefaultJspxPage jspxPage)) {
      throw new ExtensionException("Namespace obfuscation can only be applied to JspxPage");
    }

    String namespace = ctx.getStringInput("namespace");
    if (namespace == null) {
      namespace = "jsp";
    }

    jspxPage.setNamespace(namespace);
  }
}
