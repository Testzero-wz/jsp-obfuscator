package com.t3stzer0.jspobfuscator.plugins.extensions.PageFactory;

import static org.junit.jupiter.api.Assertions.*;

import com.t3stzer0.jspobfuscator.api.page.JspPage;
import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.DeclarationPageNode;
import com.t3stzer0.jspobfuscator.api.page.node.ImportPageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;

import java.util.Iterator;
import java.util.List;

import com.t3stzer0.jspobfuscator.plugins.common.impl.node.DefaultXmlPrologPageNode;
import com.t3stzer0.jspobfuscator.plugins.common.impl.node.DefaultXmlRootPageNode;
import org.junit.jupiter.api.Test;

class DefaultPageFactoryTest {

  @Test
  void createJspPage() {
    Page page = getPage(0);
    assertInstanceOf(JspPage.class, page);
    List<PageNode> nodes = page.getNodes();
    PageNode node;
    Iterator<PageNode> nodeIter = nodes.iterator();
    node = nodeIter.next();
    assertInstanceOf(ImportPageNode.class, node);
    assertEquals("import0", ((ImportPageNode) node).getImportString());
    node = nodeIter.next();
    assertInstanceOf(ImportPageNode.class, node);
    assertEquals("import1", ((ImportPageNode) node).getImportString());
    node = nodeIter.next();
    assertInstanceOf(DeclarationPageNode.class, node);
    assertEquals("declaration0", node.getText());
    node = nodeIter.next();
    assertInstanceOf(DeclarationPageNode.class, node);
    assertEquals("declaration1", node.getText());
    node = nodeIter.next();
    assertInstanceOf(PageNode.class, node);
    assertEquals("code0", node.getText());
    node = nodeIter.next();
    assertInstanceOf(PageNode.class, node);
    assertEquals("code1", node.getText());
  }

  @Test
  void createJspxPage() {
    Page page = getPage(1);
    assertInstanceOf(JspxPage.class, page);
    List<PageNode> nodes = page.getNodes();
    PageNode node;
    Iterator<PageNode> nodeIter = nodes.iterator();
    // xml prolog
    node = nodeIter.next();
    assertInstanceOf(DefaultXmlPrologPageNode.class, node);
    // xml root
    node = nodeIter.next();
    assertInstanceOf(DefaultXmlRootPageNode.class, node);

    node = nodeIter.next();
    assertInstanceOf(ImportPageNode.class, node);
    assertEquals("import0", ((ImportPageNode) node).getImportString());
    node = nodeIter.next();
    assertInstanceOf(ImportPageNode.class, node);
    assertEquals("import1", ((ImportPageNode) node).getImportString());
    node = nodeIter.next();
    assertInstanceOf(DeclarationPageNode.class, node);
    assertEquals("declaration0", node.getText());
    node = nodeIter.next();
    assertInstanceOf(DeclarationPageNode.class, node);
    assertEquals("declaration1", node.getText());
    node = nodeIter.next();
    assertInstanceOf(PageNode.class, node);
    assertEquals("code0", node.getText());
    node = nodeIter.next();
    assertInstanceOf(PageNode.class, node);
    assertEquals("code1", node.getText());
  }

  public Page getPage(int typeIndex) {
    List<String> imports = List.of("import0", "import1");
    List<String> codes = List.of("code0", "code1");
    List<String> declarations = List.of("declaration0", "declaration1");
    DefaultPageFactory defaultPageFactory = new DefaultPageFactory();
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    defaultPageFactory.initExtension(ctx);
    ctx.setSelectInput("Syntax", typeIndex);
    return defaultPageFactory.createPage(ctx, imports, declarations, codes);
  }
}
