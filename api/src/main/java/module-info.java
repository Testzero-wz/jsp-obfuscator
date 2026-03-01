module jspobfuscator.api {
  exports com.t3stzer0.jspobfuscator.api.page;
  exports com.t3stzer0.jspobfuscator.api.page.node;
  exports com.t3stzer0.jspobfuscator.api.page.node.context;
  exports com.t3stzer0.jspobfuscator.api.plugin.gadget;
  exports com.t3stzer0.jspobfuscator.api.plugin.obfus;
  exports com.t3stzer0.jspobfuscator.api.plugin.context;
  exports com.t3stzer0.jspobfuscator.api.plugin.exceptions;
  exports com.t3stzer0.jspobfuscator.api.page.spi;
  exports com.t3stzer0.jspobfuscator.api.page.node.spi;
  exports com.t3stzer0.jspobfuscator.api.plugin;

  requires org.pf4j;
  requires java.desktop;
}
