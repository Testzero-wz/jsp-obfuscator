package com.t3stzer0.jspobfuscator.api.plugin.obfus;

import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.plugin.BaseExtension;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import java.util.List;

public interface PageFactoryExtension extends BaseExtension {
  Page createPage(
      ExtensionContext ctx, List<String> imports, List<String> declarations, List<String> codes);
}
