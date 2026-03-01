package com.t3stzer0.jspobfuscator.plugins.common.impl.node;

import com.t3stzer0.jspobfuscator.api.page.JspPage;
import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.page.node.context.RenderContext;
import com.t3stzer0.jspobfuscator.api.page.node.spi.AbstractPageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.plugins.common.exception.RenderError;

public class DefaultXmlRootPageNode extends AbstractPageNode {
  @Override
  public void render(ExtensionContext extCtx, RenderContext renderCxt, JspPage page) {
    throw new RenderError(
        "Cannot render: PageNode "
            + this.getClass()
            + " is incompatible with Page "
            + JspPage.class);
  }

  @Override
  public void render(ExtensionContext extCtx, RenderContext renderCxt, JspxPage page) {
    String namespace = page.getNamespace();
    String endTag = "</" + namespace + ">\n";
    renderCxt.push(endTag);
    String startTag =
        "<" + namespace + " " + "xmlns:" + namespace + "=\"http://java.sun.com/JSP/Page\" >\n";
    renderCxt.append(startTag);
  }
}
