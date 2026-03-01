package com.t3stzer0.jspobfuscator.api.plugin.obfus;

import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.plugin.BaseExtension;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;

public interface OutputPageExtension extends BaseExtension {
  byte[] outputPage(ExtensionContext ctx, Page page);
}
