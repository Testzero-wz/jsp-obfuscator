package com.t3stzer0.jspobfuscator.plugins.common.impl.node;

import com.t3stzer0.jspobfuscator.api.page.JspPage;
import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.ImportPageNode;
import com.t3stzer0.jspobfuscator.api.page.node.context.RenderContext;
import com.t3stzer0.jspobfuscator.api.page.node.spi.AbstractPageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspPage;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspxPage;
import com.t3stzer0.jspobfuscator.plugins.common.utils.PageNodeUtils;

public class DefaultImportPageNode extends AbstractPageNode implements ImportPageNode {
  private final String IMPORT_KEY = "import";

  @Override
  public void addImport(String importString) {

    String raw = this.getAttribute(IMPORT_KEY);
    if (raw == null) {
      this.setAttribute(IMPORT_KEY, importString);
    } else {
      this.setAttribute(IMPORT_KEY, raw + "," + importString);
    }
  }

  @Override
  public String getImportString() {
    return this.getAttribute(IMPORT_KEY);
  }

  @Override
  public void render(ExtensionContext ctx, RenderContext renderContext, JspPage page) {
    renderContext.append("<%@ page ");
    renderContext.append(PageNodeUtils.outputAttributes(this));
    renderContext.append(" %>\n");
  }

  @Override
  public void render(ExtensionContext ctx, RenderContext renderContext, JspxPage page) {
    renderContext.append("<").append(page.getNamespace()).append(":directive.page ");
    renderContext.append(PageNodeUtils.outputAttributes(this));
    renderContext.append(" />\n");
  }
}
