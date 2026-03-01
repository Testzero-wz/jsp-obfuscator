package com.t3stzer0.jspobfuscator.plugins.common.impl.node;

import com.t3stzer0.jspobfuscator.api.page.JspPage;
import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.context.RenderContext;
import com.t3stzer0.jspobfuscator.api.page.node.spi.AbstractPageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.plugins.common.utils.PageNodeUtils;

public class DefaultCodePageNode extends AbstractPageNode implements CodePageNode {

  @Override
  public void render(ExtensionContext extensionContext, RenderContext renderContext, JspPage page) {
    renderContext.append("<%\n");
    renderContext.append(this.getText());
    renderContext.append("\n%>\n");
  }

  @Override
  public void render(
      ExtensionContext extensionContext, RenderContext renderContext, JspxPage page) {
    renderContext.append("<").append(page.getNamespace()).append(":scriptlet ");
    renderContext.append(PageNodeUtils.outputAttributes(this));
    renderContext.append(">\n");
    renderContext.append(this.getText());
    renderContext.append("\n</").append(page.getNamespace()).append(":scriptlet>\n");
  }
}
