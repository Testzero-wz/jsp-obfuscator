package com.t3stzer0.jspobfuscator.api.page.spi;

import com.t3stzer0.jspobfuscator.api.page.JspxPage;

public abstract class AbstractJspxPage extends AbstractPage implements JspxPage {
  protected String namespace;
  protected String pageEncoding;

  @Override
  public String getNamespace() {
    return namespace;
  }

  @Override
  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public void setPageEncoding(String pageEncoding) {
    this.pageEncoding = pageEncoding;
  }

  public String getPageEncoding() {
    return pageEncoding;
  }
}
