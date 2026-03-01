package com.t3stzer0.jspobfuscator.plugins.node;

import com.t3stzer0.jspobfuscator.api.page.JspPage;
import com.t3stzer0.jspobfuscator.api.page.JspxPage;
import com.t3stzer0.jspobfuscator.api.page.node.CodePageNode;
import com.t3stzer0.jspobfuscator.api.page.node.context.RenderContext;
import com.t3stzer0.jspobfuscator.api.page.node.spi.AbstractPageNode;
import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;

public class CommentCodePageNode extends AbstractPageNode implements CodePageNode {

    @Override
    public void render(ExtensionContext extCtx, RenderContext renderCxt, JspPage page) {

    }

    @Override
    public void render(ExtensionContext extCtx, RenderContext renderCxt, JspxPage page) {

    }
}
