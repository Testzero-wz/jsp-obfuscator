package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.DeclarationPageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.EditPageExtension;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.plugins.common.utils.random.RandomGenerator;
import com.t3stzer0.jspobfuscator.plugins.common.utils.string.htmlEntity;
import org.pf4j.Extension;

import java.util.List;

@Extension
public class HtmlEntityEncode implements EditPageExtension {
  static final List<String> options = List.of("hex", "decimal");
  static final int repeatBound = 10;

  @Override
  public void initExtension(ExtensionContext ctx) {
    ctx.registerSelectInput("radix", options, true, 0);
  }

  @Override
  public void editPage(ExtensionContext ctx, Page page) {
    if (!(page instanceof JspxPage)) {
      throw new ExtensionException("Page is not a JspxPage");
    }

    String radix = options.get(ctx.getSelectInput("radix"));
    RandomGenerator rng = new RandomGenerator(ctx.getRandomSeed());

    for (PageNode node : page.getNodes()) {
      if (!(node instanceof CodePageNode || node instanceof DeclarationPageNode)) {
        continue;
      }
      StringBuilder out = new StringBuilder();
      for (char c : node.getText().toCharArray()) {
        String encoded =
            switch (radix) {
              case "hex" -> htmlEntity.encodeHex(c, rng.nextInt(repeatBound));
              case "decimal" -> htmlEntity.encodeDecimal(c, rng.nextInt(repeatBound));
              default -> throw new ExtensionException("Invalid radix: " + radix);
            };
        out.append(encoded);
      }
      node.setText(out.toString());
    }
  }
}
