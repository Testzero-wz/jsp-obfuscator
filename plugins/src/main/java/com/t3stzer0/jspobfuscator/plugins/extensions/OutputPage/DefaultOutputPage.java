package com.t3stzer0.jspobfuscator.plugins.extensions.OutputPage;

import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.page.node.context.RenderContextAdapter;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.OutputPageExtension;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.pf4j.Extension;

@Extension
public class DefaultOutputPage implements OutputPageExtension {
  @Override
  public byte[] outputPage(ExtensionContext ctx, Page page) {
    RenderContextAdapter renderContext = new RenderContextAdapter();
    boolean compact = ctx.getSelectInput("CompactMode") == 1;
    for (PageNode node : page.getNodes()) {
      page.accept(ctx, renderContext, node);
    }
    while (!renderContext.stack().isEmpty()) {
      renderContext.append(renderContext.pop());
    }
    String renderString = renderContext.render();
    if (compact) {
      renderString = renderString.replaceAll("(\\r?\\n\\s*)+", "");
    }
    return renderString.getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public void initExtension(ExtensionContext ctx) {
    ctx.registerSelectInput("CompactMode", List.of("False", "True"), true, 0);
  }
}
