package com.t3stzer0.jspobfuscator.plugins.extensions.OutputPage;

import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.DeclarationPageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.page.node.context.RenderContext;
import com.t3stzer0.jspobfuscator.api.page.node.context.RenderContextAdapter;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.OutputPageExtension;
import com.t3stzer0.jspobfuscator.plugins.common.utils.random.RandomGenerator;
import com.t3stzer0.jspobfuscator.plugins.common.utils.string.htmlEntity;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.apache.commons.text.StringSubstitutor;
import org.pf4j.Extension;

@Extension
public class SetPropertyOutputPage implements OutputPageExtension {
  List<String> pageContextAttributes =
      List.of(
          "javax.servlet.jsp.jspRequest",
          "javax.servlet.jsp.jspPageContext",
          "javax.servlet.jsp.jspResponse",
          "javax.servlet.jsp.jspSession",
          "javax.servlet.jsp.jspApplication",
          "javax.servlet.jsp.jspOut",
          "javax.servlet.jsp.jspPage",
          "javax.servlet.jsp.jspConfig");

  @Override
  public byte[] outputPage(ExtensionContext ctx, Page page) {
    if (!(page instanceof JspxPage)) {
      throw new ExtensionException("Page is not an instance of JspxPage");
    }

    // all declarations must appear before code
    // because setProperty relies on a code-escaping workaround
    // HACK: re-arrange nodes to comply with the requirement
    int index = -1;
    for (int i = 0; i < page.getNodes().size(); i++) {
      if (page.getNodes().get(i) instanceof CodePageNode) {
        index = i;
        break;
      }
    }
    if (index != -1) {
      ArrayList<PageNode> declarations = new ArrayList<>();
      Iterator<PageNode> it = page.getNodes().iterator();
      while (it.hasNext()) {
        PageNode node = it.next();
        if (node instanceof DeclarationPageNode) {
          declarations.add(node);
          it.remove();
        }
      }
      page.getNodes().addAll(index, declarations);
    }

    // render page
    RenderContext renderContext = new RenderContextAdapter();
    RandomGenerator rng = new RandomGenerator(ctx.getRandomSeed());
    String namespace = ((JspxPage) page).getNamespace();
    for (PageNode node : page.getNodes()) {
      if (node instanceof CodePageNode || node instanceof DeclarationPageNode) {
        renderContext.append(craft(node.getText(), namespace, rng));
      } else {
        page.accept(ctx, renderContext, node);
      }
    }
    while (!renderContext.stack().isEmpty()) {
      renderContext.append(renderContext.pop());
    }
    return renderContext.render().getBytes(StandardCharsets.UTF_8);
  }

  String craft(String code, String namespace, RandomGenerator rng) {
    String tag = "<" + namespace + ":setProperty name='%s' property=\"*\" />\n";
    String pageContextAttribute =
        pageContextAttributes.get(rng.nextInt(pageContextAttributes.size()));
    HashMap<String, String> values = new HashMap<>();
    values.put("code", code);
    values.put("pageContextAttribute", pageContextAttribute);
    StringSubstitutor sub = new StringSubstitutor(values);
    String payloadTemplate =
"""
${pageContextAttribute}"), request);
${code}
//""";
    String payload = sub.replace(payloadTemplate);
    StringBuilder escapedCode = new StringBuilder();
    int repeatBound = 10;
    for (char c : payload.toCharArray()) {
      if (rng.nextBoolean()) {
        escapedCode.append(htmlEntity.encodeHex(c, rng.nextInt(repeatBound)));
      } else {
        escapedCode.append(htmlEntity.encodeDecimal(c, rng.nextInt(repeatBound)));
      }
    }
    return String.format(tag, escapedCode);
  }
}
