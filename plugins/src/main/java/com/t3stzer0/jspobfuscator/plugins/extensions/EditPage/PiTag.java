package com.t3stzer0.jspobfuscator.plugins.extensions.EditPage;

import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.DeclarationPageNode;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.exceptions.ExtensionException;
import com.t3stzer0.jspobfuscator.api.plugin.obfus.EditPageExtension;
import com.t3stzer0.jspobfuscator.plugins.common.utils.random.RandomGenerator;
import org.pf4j.Extension;

@Extension
public class PiTag implements EditPageExtension {

    @Override
    public void editPage(ExtensionContext ctx, Page page) {
        if(!(page instanceof JspxPage)){
            throw new ExtensionException("Page is not a JspxPage");
        }
        RandomGenerator rng = new RandomGenerator(ctx.getRandomSeed());
        for(PageNode node : page.getNodes()){
            if( !(node instanceof CodePageNode || node instanceof DeclarationPageNode)){
                continue;
            }
            String code = node.getText();
            StringBuilder obfus = new StringBuilder();
            for(int i = 0; i < code.length() - 1; i++){
                char c = code.charAt(i);
                obfus.append(c);
                obfus.append("<?");
                obfus.append(rng.nextChar()).append(rng.nextChar());
                obfus.append(" ?>");
            }
            obfus.append(code.charAt(code.length() - 1));
            node.setText(obfus.toString());
        }
    }

}
