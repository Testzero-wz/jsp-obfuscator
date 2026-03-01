package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.DeclarationPageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.EditPageExtension;
import com.t3stzer0.jspobfuscator.plugins.common.impl.node.DefaultDeclarationPageNode;
import com.t3stzer0.jspobfuscator.plugins.common.impl.node.DefaultImportPageNode;
import com.t3stzer0.jspobfuscator.plugins.common.utils.string.replace;
import org.pf4j.Extension;

import java.util.Base64;
import java.util.function.Function;

@Extension
public class QuotedStringReplaceB64 implements EditPageExtension {
  @Override
  public void editPage(ExtensionContext ctx, Page page) {

    for (PageNode node : page.getNodes()) {
      if (!(node instanceof CodePageNode || node instanceof DeclarationPageNode)) {
        continue;
      }
      String code = node.getText();
      Function<String, String> f =
          s -> {
            String encoded = Base64.getEncoder().encodeToString(s.getBytes());
            return "new String(java.util.Base64.getDecoder().decode(\"" + encoded + "\"))";
          };
      node.setText(replace.ReplaceQuoteStringWithFunc(code, f));
    }
  }
}
