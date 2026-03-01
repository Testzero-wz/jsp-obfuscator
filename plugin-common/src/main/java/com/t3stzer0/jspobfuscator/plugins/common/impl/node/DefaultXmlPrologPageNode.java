package com.t3stzer0.jspobfuscator.plugins.common.impl.node;

import com.t3stzer0.jspobfuscator.api.page.JspPage;
import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.page.node.context.RenderContext;
import com.t3stzer0.jspobfuscator.api.page.node.spi.AbstractPageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.plugins.common.exception.RenderError;
import com.t3stzer0.jspobfuscator.plugins.common.utils.PageNodeUtils;

public class DefaultXmlPrologPageNode extends AbstractPageNode {
  String DEFAULT_ENCODING = "UTF-8";
  String DEFAULT_VERSION = "1.0";

  public DefaultXmlPrologPageNode() {
    this.setAttribute("version", DEFAULT_VERSION);
    this.setAttribute("encoding", DEFAULT_ENCODING);
  }

  @Override
  public void render(ExtensionContext ctx, RenderContext renderContext, JspPage page) {
    throw new RenderError(
        "Cannot render: PageNode "
            + this.getClass()
            + " is incompatible with Page "
            + JspxPage.class);
  }

  @Override
  public void render(ExtensionContext ctx, RenderContext renderContext, JspxPage page) {
    renderContext.append("<?xml " + PageNodeUtils.outputAttributes(this) + " ?>\n");
  }
}
