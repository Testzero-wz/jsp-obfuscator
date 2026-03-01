package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.EditPageExtension;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.plugins.common.impl.node.DefaultCodePageNode;
import java.util.ListIterator;
import org.pf4j.Extension;

@Extension
public class CodeSplit implements EditPageExtension {
  @Override
  public void editPage(ExtensionContext ctx, Page page) {
    ListIterator<PageNode> it = page.getNodes().listIterator();
    PageNode node;
    while (it.hasNext()) {
      node = it.next();
      if (!(node instanceof CodePageNode)) {
        continue;
      }
      String rawCode = node.getText();
      it.remove();
      for (String code : rawCode.split("\\R+")) {
        code = code.trim();
        if (code.isEmpty()) {
          continue;
        }
        DefaultCodePageNode codeNode = new DefaultCodePageNode();
        codeNode.setText(code);
        it.add(codeNode);
      }
    }
  }
}
