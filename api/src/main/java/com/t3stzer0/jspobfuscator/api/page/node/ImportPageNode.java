package com.t3stzer0.jspobfuscator.api.page.node;

public interface ImportPageNode {
  void addImport(String importString);

  String getImportString();
}
