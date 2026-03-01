package com.t3stzer0.jspobfuscator.plugins.common.impl.page;

import com.t3stzer0.jspobfuscator.api.page.spi.AbstractJspPage;
import com.t3stzer0.jspobfuscator.plugins.common.impl.node.DefaultCodePageNode;
import com.t3stzer0.jspobfuscator.plugins.common.impl.node.DefaultDeclarationPageNode;
import com.t3stzer0.jspobfuscator.plugins.common.impl.node.DefaultImportPageNode;
import java.util.List;

public class DefaultJspPage extends AbstractJspPage {
  public DefaultJspPage() {}

  public DefaultJspPage(List<String> imports, List<String> declarations, List<String> codes) {
    for (String importString : imports) {
      DefaultImportPageNode node = new DefaultImportPageNode();
      node.addImport(importString);
      this.addNode(node);
    }
    for (String declaration : declarations) {
      DefaultDeclarationPageNode node = new DefaultDeclarationPageNode();
      node.setText(declaration);
      this.addNode(node);
    }
    for (String code : codes) {
      DefaultCodePageNode node = new DefaultCodePageNode();
      node.setText(code);
      this.addNode(node);
    }
  }
}
