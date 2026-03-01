package com.t3stzer0.jspobfuscator.api.page;

import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.page.node.context.RenderContext;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;

import java.util.List;

public interface Page {
  List<PageNode> getNodes();

  void addNode(PageNode node);

  default void accept(ExtensionContext ctx, RenderContext renderContext, PageNode node) {
    node.render(ctx, renderContext, this);
  }
}
