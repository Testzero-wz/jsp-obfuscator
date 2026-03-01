package com.t3stzer0.jspobfuscator.plugins.extensions.OutputPage;

import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import com.t3stzer0.jspobfuscator.plugins.common.impl.node.DefaultXmlPrologPageNode;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspPage;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspxPage;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DoubleEncodeOutputPageTest {

  List<String> imports = List.of("import1", "import2");
  List<String> declarations = List.of("declaration1", "declaration2");
  List<String> codes = List.of("code1", "code2");

  @Test
  void testAutoAlign() {
    String xmlPrologTemplate = "<?xml version=\"1.0\" encoding='%s'?>";
    DefaultOutputPage outputExt = new DefaultOutputPage();
    ExtensionContextAdapter outputExtCtx = new ExtensionContextAdapter();
    outputExt.initExtension(outputExtCtx);
    String pageRender = new String(outputExt.outputPage(outputExtCtx, getDefaultJspxPage()));
    for (String first : DoubleEncodeOutputPage.firstEncoding) {
      for (String second : DoubleEncodeOutputPage.secondEncoding) {
        Charset firstCharset = Charset.forName(first);
        Charset secondCharset = Charset.forName(second);
        String xmlProlog = String.format(xmlPrologTemplate, second);
        String aligned = DoubleEncodeOutputPage.autoAlign(xmlProlog, firstCharset, secondCharset);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.writeBytes(aligned.getBytes(firstCharset));
        out.writeBytes(pageRender.getBytes(secondCharset));
        assertTrue(
            secondCharset
                .decode(ByteBuffer.wrap(out.toByteArray()))
                .toString()
                .contains(pageRender));
      }
    }
  }

  DefaultJspxPage getDefaultJspxPage() {
    return new DefaultJspxPage(imports, declarations, codes);
  }

  DefaultJspPage getDefaultJspPage() {
    return new DefaultJspPage(imports, declarations, codes);
  }

  @Test
  void outputJspPage() {
    DoubleEncodeOutputPage extension = new DoubleEncodeOutputPage();
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    extension.initExtension(ctx);
    // cp037
    ctx.setSelectInput("firstEncoding", 1);
    // utf-16le
    ctx.setSelectInput("secondEncoding", 2);
    DefaultJspPage page = getDefaultJspPage();
    byte[] out = extension.outputPage(ctx, page);
    String expectedPageDirective = "<?xm<%@  page pageEncoding='UTF-16LE' %>";

    DefaultOutputPage defaultOutputPage = new DefaultOutputPage();
    ExtensionContextAdapter defaultExtCtx = new ExtensionContextAdapter();
    defaultOutputPage.initExtension(defaultExtCtx);

    String expectedRestRender = new String(defaultOutputPage.outputPage(defaultExtCtx, page));
    String firstDecoded = Charset.forName("cp037").decode(ByteBuffer.wrap(out)).toString();
    assertTrue(firstDecoded.contains(expectedPageDirective));
    String secondDecoded = Charset.forName("utf-16le").decode(ByteBuffer.wrap(out)).toString();
    assertTrue(secondDecoded.contains(expectedRestRender));
  }

  @Test
  void outputJspxPage() {
    DoubleEncodeOutputPage extension = new DoubleEncodeOutputPage();
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    extension.initExtension(ctx);
    // cp037
    ctx.setSelectInput("firstEncoding", 1);
    // utf-16le
    ctx.setSelectInput("secondEncoding", 2);
    DefaultJspxPage page = getDefaultJspxPage();
    byte[] out = extension.outputPage(ctx, page);
    String expectedXmlProlog = "<?xml version=\"1.0\" encoding=\"UTF-16LE\" ?>";
    page.getNodes().removeIf(node -> node instanceof DefaultXmlPrologPageNode);

    DefaultOutputPage defaultOutputPage = new DefaultOutputPage();
    ExtensionContextAdapter defaultExtCtx = new ExtensionContextAdapter();
    defaultOutputPage.initExtension(defaultExtCtx);

    String expectedRestRender = new String(defaultOutputPage.outputPage(defaultExtCtx, page));
    String firstDecoded = Charset.forName("cp037").decode(ByteBuffer.wrap(out)).toString();
    assertTrue(firstDecoded.contains(expectedXmlProlog));
    String secondDecoded = Charset.forName("utf-16le").decode(ByteBuffer.wrap(out)).toString();
    assertTrue(secondDecoded.contains(expectedRestRender));
  }
}
