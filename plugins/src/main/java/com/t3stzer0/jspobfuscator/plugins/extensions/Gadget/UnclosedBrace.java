package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.gadget.GadgetExtension;
import org.pf4j.Extension;

import java.util.List;

@Extension
public class UnclosedBrace implements GadgetExtension {
    @Override
    public List<String> getCode(ExtensionContext ctx) {
    return List.of(
"""
} catch (java.lang.Throwable t) {}
""",
"""
try{
    // type your code here
    out.println("it works");
""");
    }

    @Override
    public List<String> getDeclaration(ExtensionContext ctx) {
        return List.of();
    }

    @Override
    public List<String> getImports(ExtensionContext ctx) {
        return List.of();
    }
}
