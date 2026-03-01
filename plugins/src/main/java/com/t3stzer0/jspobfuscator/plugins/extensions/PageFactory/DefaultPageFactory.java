package com.t3stzer0.jspobfuscator.plugins.extensions.PageFactory;

import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.PageFactoryExtension;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspPage;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspxPage;
import java.util.Arrays;
import java.util.List;
import org.pf4j.Extension;

@Extension
public class DefaultPageFactory implements PageFactoryExtension {

  List<String> SyntaxTypes = Arrays.asList("JSP", "JSPX");

  @Override
  public void initExtension(ExtensionContext ctx) {
    ctx.registerSelectInput("Syntax", SyntaxTypes, true, 0);
  }

  @Override
  public Page createPage(
      ExtensionContext ctx, List<String> imports, List<String> declarations, List<String> codes) {
    String syntax = SyntaxTypes.get(ctx.getSelectInput("Syntax"));
    return switch (syntax) {
      case "JSP" -> new DefaultJspPage(imports, declarations, codes);
      case "JSPX" ->new  DefaultJspxPage(imports, declarations, codes);
      default -> throw new ExtensionException("Invalid syntax type: " + syntax);
    };
  }
}
