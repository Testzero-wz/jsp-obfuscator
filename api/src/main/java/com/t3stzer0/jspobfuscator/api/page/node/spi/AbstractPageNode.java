package com.t3stzer0.jspobfuscator.api.page.node.spi;

import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractPageNode implements PageNode {
  protected String id = "";
  protected String text = "";
  protected Map<String, String> attributes = new LinkedHashMap<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Map<String, String> getAttributes() {
    return attributes;
  }

  public String getAttribute(String name) {
    return attributes.get(name);
  }

  @Override
  public void removeAttribute(String key) {
    attributes.remove(key);
  }

  @Override
  public void setAttribute(String key, String value) {
    attributes.put(key, value);
  }

  public void clearAttributes() {
    this.attributes.clear();
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
