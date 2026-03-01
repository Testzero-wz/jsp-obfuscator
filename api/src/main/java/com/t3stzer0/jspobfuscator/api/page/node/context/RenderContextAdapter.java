package com.t3stzer0.jspobfuscator.api.page.node.context;

import java.util.Stack;

public class RenderContextAdapter extends AbstractRenderContext {
  public RenderContextAdapter() {
    super();
    stack = new Stack<>();
    outputBuffer = new StringBuilder();
  }
}
