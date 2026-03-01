package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.DeclarationPageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.EditPageExtension;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.plugins.common.utils.random.RandomGenerator;
import org.pf4j.Extension;


@Extension
public class UnicodeEncode implements EditPageExtension {
  @Override
  public void editPage(ExtensionContext ctx, Page page) {
    RandomGenerator rng = new RandomGenerator(ctx.getRandomSeed());
    // ref: org.eclipse.jdt.internal.compiler.parser.Scanner#getNextUnicodeChar
    for (PageNode node : page.getNodes()) {
      if (!(node instanceof CodePageNode || node instanceof DeclarationPageNode)) {
        continue;
      }
      String code = node.getText();
      node.setText(unicodeEncode(code, rng));
    }
  }

  String unicodeEncode(String data, RandomGenerator rng) {
    StringBuilder out = new StringBuilder();
    for (char c : data.toCharArray()) {
      out.append("\\");
      out.append("u".repeat(rng.nextInt(1, 10)));
      if (rng.nextBoolean()) {
        out.append(String.format("%04x", (int) c));
      } else {
        out.append(String.format("%04X", (int) c));
      }
    }
    return out.toString();
  }
}
