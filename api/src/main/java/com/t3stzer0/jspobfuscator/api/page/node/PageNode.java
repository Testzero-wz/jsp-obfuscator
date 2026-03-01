package com.t3stzer0.jspobfuscator.api.page.node;

import com.t3stzer0.jspobfuscator.api.page.JspPage;
import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.context.RenderContext;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import java.util.Map;

public interface PageNode {

  String getAttribute(String key);

  Map<String, String> getAttributes();

  void removeAttribute(String key);

  void setAttribute(String key, String value);

  void clearAttributes();

  String getText();

  void setText(String text);

  default void render(ExtensionContext ctx, RenderContext renderCxt, Page page) {}

  void render(ExtensionContext extCtx, RenderContext renderCxt, JspPage page);

  void render(ExtensionContext extCtx, RenderContext renderCxt, JspxPage page);
}
