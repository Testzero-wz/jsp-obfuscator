package com.t3stzer0.jspobfuscator.plugins.common.impl.page;

import com.t3stzer0.jspobfuscator.api.page.spi.AbstractJspxPage;
import com.t3stzer0.jspobfuscator.plugins.common.impl.node.*;

import java.util.List;

public class DefaultJspxPage extends AbstractJspxPage {

  public DefaultJspxPage() {
    setDefaultValues();
  }

  public DefaultJspxPage(List<String> imports, List<String> declarations, List<String> codes) {
    setDefaultValues();
    // add xml prolog node
    this.addNode(new DefaultXmlPrologPageNode());
    // add xml root node
    this.addNode(new DefaultXmlRootPageNode());

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

  private void setDefaultValues() {
    namespace = "jsp";
    pageEncoding = "UTF-8";
  }
}
