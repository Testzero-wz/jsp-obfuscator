package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.DeclarationPageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.EditPageExtension;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import org.pf4j.Extension;

@Extension
public class Cdata implements EditPageExtension {

  private static final String CDATA_START = "<![CDATA[";
  private static final String CDATA_END = "]]>";

  @Override
  public void editPage(ExtensionContext ctx, Page page) {
    if (!(page instanceof JspxPage)) {
      throw new ExtensionException("Cdata only support JspxPage");
    }
    for (PageNode node : page.getNodes()) {
      if (!(node instanceof CodePageNode || node instanceof DeclarationPageNode)) {
        continue;
      }
      // do replace
      String raw = node.getText();
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < raw.length(); i++) {
        sb.append(CDATA_START);
        sb.append(raw.charAt(i));
        sb.append(CDATA_END);
      }
      node.setText(sb.toString());
    }
  }
}
