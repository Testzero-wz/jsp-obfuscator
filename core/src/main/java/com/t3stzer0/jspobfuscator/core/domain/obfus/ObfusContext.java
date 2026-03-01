package com.t3stzer0.jspobfuscator.core.domain.obfus;

import java.util.ArrayList;
import java.util.List;

public class ObfusContext {

  // source
  public List<String> imports, declaration, code;

  // selected extensions
  public String pageFactoryExtension;
  public List<String> editPageExtensions = new ArrayList<>();
  public String outputPageExtension;

  public List<String> getImports() {
    return imports;
  }

  public List<String> getDeclaration() {
    return declaration;
  }

  public List<String> getCode() {
    return code;
  }

  public String getPageFactoryExtension() {
    return pageFactoryExtension;
  }

  public List<String> getEditPageExtensions() {
    return editPageExtensions;
  }

  public String getOutputPageExtension() {
    return outputPageExtension;
  }
}
