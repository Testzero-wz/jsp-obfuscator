package com.t3stzer0.jspobfuscator.api.page;

import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.page.node.context.RenderContext;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;

public interface JspxPage extends Page {
  String getNamespace();

  void setNamespace(String namespace);

  @Override
  default void accept(ExtensionContext ctx, RenderContext renderContext, PageNode node) {
    node.render(ctx, renderContext, this);
  }
}
