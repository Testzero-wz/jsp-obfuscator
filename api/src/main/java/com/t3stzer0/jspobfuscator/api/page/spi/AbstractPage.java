package com.t3stzer0.jspobfuscator.api.page.spi;

import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPage implements Page {
  List<PageNode> nodes = new ArrayList<>();

  @Override
  public List<PageNode> getNodes() {
    return nodes;
  }

  @Override
  public void addNode(PageNode node) {
    nodes.add(node);
  }
}
