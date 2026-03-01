package com.t3stzer0.jspobfuscator.plugins.extensions.OutputPage;

import com.t3stzer0.jspobfuscator.api.page.JspPage;
import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.page.node.context.RenderContextAdapter;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.OutputPageExtension;
import org.pf4j.Extension;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

@Extension
public class DoubleEncodeOutputPage implements OutputPageExtension {

  // https://github.com/apache/tomcat/blame/8.5.15/java/org/apache/jasper/compiler/EncodingDetector.java#L129C6-L129C6
  static final List<String> firstEncodingOptions =
      List.of(
          "UTF-8",
          // isBomPresent fix >= 8.5.15
          // commit ref:
          // https://github.com/apache/tomcat/commit/c29a2b45f57e481380d88a8fa0c6f4f0f242aca1
          "CP037(Tomcat ≥ 8.5.15)",
          "UTF-16LE(Tomcat ≥ 8.5.15)",
          "UTF-16BE(Tomcat ≥ 8.5.15)");
  static final List<String> firstEncoding = List.of("UTF-8", "CP037", "UTF-16LE", "UTF-16BE");
  // support charset: https://docs.oracle.com/en/java/javase/21/intl/supported-encodings.html
  static final List<String> secondEncoding =
      List.of(
          "UTF-8",
          "CP037",
          "UTF-16LE",
          "UTF-16BE",
          "UTF-32LE",
          "UTF-32BE",
          "IBM-037",
          "IBM-290",
          "IBM-930");

  @Override
  public void initExtension(ExtensionContext ctx) {
    ctx.registerSelectInput("firstEncoding", firstEncodingOptions, true, 0);
    ctx.registerSelectInput("secondEncoding", secondEncoding, true, 1);
  }

  @Override
  public byte[] outputPage(ExtensionContext ctx, Page page) {
    if (!(page instanceof JspPage || page instanceof JspxPage)) {
      throw new ExtensionException("Unsupported page type");
    }
    if (ctx.getSelectInput("firstEncoding") == -1 || ctx.getSelectInput("secondEncoding") == -1) {
      throw new ExtensionException("Encoding not selected");
    }

    String first = firstEncoding.get(ctx.getSelectInput("firstEncoding"));
    String second = secondEncoding.get(ctx.getSelectInput("secondEncoding"));

    Charset firstCharset;
    Charset secondCharset;
    try {
      firstCharset = Charset.forName(first);
      secondCharset = Charset.forName(second);
    } catch (Exception e) {
      throw new ExtensionException("Invalid encoding");
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    RenderContextAdapter renderContext = new RenderContextAdapter();
    Iterator<PageNode> iterator = page.getNodes().iterator();
    PageNode node;
    if (page instanceof JspPage) {
      // jsp 语法也会读取xml prolog的前序判断编码，但是如果!bomPresent的话，会丢弃之前的编码
      // 重新以 page 标签的encoding为准，如果没有则回落到utf-8
      // 所以要实现双重编码，目前只能是:
      // 1. bomPresent: encoding with bom -> xml prolog encoding
      // 2. !bomPresent: encoding without bom(need some hack) -> page tag encoding
      // 由于jsp和jspx都统一without bom会方便实现一些，目前选择第二种方案

      // 实现encoding without bom, jsp中会被视为plain text
      // https://github.com/apache/tomcat/blame/8.5.15/java/org/apache/jasper/compiler/EncodingDetector.java#L129C6-L129C6
      String hackXmlProlog;
      if (firstCharset.equals(StandardCharsets.UTF_8)) {
        hackXmlProlog = "";
      } else if (firstCharset.equals(StandardCharsets.UTF_16BE)
          || firstCharset.equals(StandardCharsets.UTF_16LE)) {
        hackXmlProlog = "<?";
      } else {
        // should be cp037
        // need four bytes
        hackXmlProlog = "<?xm";
      }
      String pageDirectiveRender = "<%@ page pageEncoding='" + second + "' %>";
      String concat = hackXmlProlog + pageDirectiveRender;
      out.writeBytes(autoAlign(concat, firstCharset, secondCharset).getBytes(firstCharset));

    } else {
      // jspx page

      // xml prolog
      node = iterator.next();
      node.setAttribute("encoding", second);
      page.accept(ctx, renderContext, node);
      String xmlProlog = renderContext.render();
      renderContext.clearBuffer();
      if (!xmlProlog.startsWith("<?xml")) {
        throw new ExtensionException("Jspx page must start with xml prolog");
      }

      if (!xmlProlog.contains(second)) {
        throw new ExtensionException("Fail to set encoding in xml prolog");
      }

      out.writeBytes(autoAlign(xmlProlog, firstCharset, secondCharset).getBytes(firstCharset));
    }

    while (iterator.hasNext()) {
      node = iterator.next();
      page.accept(ctx, renderContext, node);
    }
    while (!renderContext.stack().isEmpty()) {
      renderContext.append(renderContext.pop());
    }
    out.writeBytes(renderContext.render().getBytes(secondCharset));
    return out.toByteArray();
  }

  // 双重编码改变的只是改变两次字符编码，
  // 而最后的字符编码会决定整个文本的对齐方式
  // 尝试自动对齐
  // https://tttang.com/archive/1840/#toc__5
  static String autoAlign(String encodingNodeRender, Charset first, Charset second) {
    encodingNodeRender = encodingNodeRender.strip();
    int spaceIdx = encodingNodeRender.indexOf(" ");
    String prefix = encodingNodeRender.substring(0, spaceIdx);
    String suffix = encodingNodeRender.substring(spaceIdx);
    String testAscii = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ<?:/>";
    for (int i = 0; i <= 10; i++) {
      String aligned = prefix + " ".repeat(i) + suffix;
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      out.writeBytes(aligned.getBytes(first));
      out.writeBytes(testAscii.getBytes(second));
      try {
        String secondDecoded = second.decode(ByteBuffer.wrap(out.toByteArray())).toString();
        if (secondDecoded.contains(testAscii)) {
          return aligned;
        }
      } catch (Exception ignored) {
      }
    }
    throw new ExtensionException("failed to auto align, try another second encoding!");
  }
}
