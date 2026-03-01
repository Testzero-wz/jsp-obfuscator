package com.t3stzer0.jspobfuscator.plugins.common.impl.node;

import com.t3stzer0.jspobfuscator.api.page.JspPage;
import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.DeclarationPageNode;
import com.t3stzer0.jspobfuscator.api.page.node.context.RenderContext;
import com.t3stzer0.jspobfuscator.api.page.node.spi.AbstractPageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspPage;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspxPage;
import com.t3stzer0.jspobfuscator.plugins.common.utils.PageNodeUtils;

public class DefaultDeclarationPageNode extends AbstractPageNode implements DeclarationPageNode {

  @Override
  public void render(ExtensionContext ctx, RenderContext renderContext, JspPage page) {
    renderContext.append("<%!\n");
    renderContext.append(this.getText());
    renderContext.append("\n%>\n");
  }

  @Override
  public void render(ExtensionContext ctx, RenderContext renderContext, JspxPage page) {
    renderContext.append("<").append(page.getNamespace()).append(":declaration ");
    renderContext.append(PageNodeUtils.outputAttributes(this));
    renderContext.append(">\n");
    renderContext.append(this.getText());
    renderContext.append("\n</").append(page.getNamespace()).append(":declaration>\n");
  }
}
