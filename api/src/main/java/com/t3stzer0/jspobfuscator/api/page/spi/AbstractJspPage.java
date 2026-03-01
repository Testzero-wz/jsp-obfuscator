package com.t3stzer0.jspobfuscator.api.page.spi;

import com.t3stzer0.jspobfuscator.api.page.JspPage;

public abstract class AbstractJspPage extends AbstractPage implements JspPage {
  protected String pageEncoding;

  public String getPageEncoding() {
    return pageEncoding;
  }

  public void setPageEncoding(String pageEncoding) {
    this.pageEncoding = pageEncoding;
  }
}
