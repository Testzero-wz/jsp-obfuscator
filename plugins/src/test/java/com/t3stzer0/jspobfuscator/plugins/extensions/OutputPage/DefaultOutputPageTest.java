package com.t3stzer0.jspobfuscator.plugins.extensions.OutputPage;

import static org.junit.jupiter.api.Assertions.*;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspPage;
import java.util.List;

import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspxPage;
import org.junit.jupiter.api.Test;

class DefaultOutputPageTest {

  private List<String> getCode() {
    return List.of("aaa", "bbb");
  }

  private List<String> getDeclaration() {
    return List.of("ccc", "ddd");
  }

  private List<String> getImports() {
    return List.of("eee", "fff");
  }

  @Test
  void outputJspPage() {
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    DefaultJspPage page = new DefaultJspPage(getImports(), getDeclaration(), getCode());
    DefaultOutputPage ext = new DefaultOutputPage();
    ext.initExtension(ctx);
    String output = new String(ext.outputPage(ctx, page));
    String expected =
        """
                <%@ page import="eee" %>
                <%@ page import="fff" %>
                <%!
                ccc
                %>
                <%!
                ddd
                %>
                <%
                aaa
                %>
                <%
                bbb
                %>
                """;
    assertEquals(expected, output);
  }

  @Test
  void outputJspxPage() {
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    DefaultJspxPage page = new DefaultJspxPage(getImports(), getDeclaration(), getCode());
    DefaultOutputPage ext = new DefaultOutputPage();
    ext.initExtension(ctx);
    String output = new String(ext.outputPage(ctx, page));
    String expected =
"""
<?xml version="1.0" encoding="UTF-8" ?>
<jsp xmlns:jsp="http://java.sun.com/JSP/Page" >
<jsp:directive.page import="eee" />
<jsp:directive.page import="fff" />
<jsp:declaration >
ccc
</jsp:declaration>
<jsp:declaration >
ddd
</jsp:declaration>
<jsp:scriptlet >
aaa
</jsp:scriptlet>
<jsp:scriptlet >
bbb
</jsp:scriptlet>
</jsp>
""";
    assertEquals(expected, output);
  }
}
