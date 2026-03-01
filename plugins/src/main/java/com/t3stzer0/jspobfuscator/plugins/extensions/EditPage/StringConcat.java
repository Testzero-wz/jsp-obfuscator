package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.DeclarationPageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.EditPageExtension;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.plugins.common.utils.string.replace;
import org.pf4j.Extension;


@Extension
public class StringConcat implements EditPageExtension {

  @Override
  public void editPage(ExtensionContext ctx, Page page) {
    for (PageNode node : page.getNodes()) {
      if (!(node instanceof CodePageNode) && !(node instanceof DeclarationPageNode)) {
        continue;
      }
      String replaced =
          replace.ReplaceQuoteStringWithFunc(
              node.getText(),
              s -> {
                StringBuilder result = new StringBuilder();
                result.append('"');
                for (int i = 0; i < s.length(); i++) {
                  result.append(s.charAt(i));
                  if (i < s.length() - 1) {
                    result.append("\"+\"");
                  }
                }
                result.append('"');
                return result.toString();
              });
      node.setText(replaced);
    }
  }
}
