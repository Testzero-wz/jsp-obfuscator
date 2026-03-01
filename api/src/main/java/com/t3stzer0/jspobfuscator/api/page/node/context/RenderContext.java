package com.t3stzer0.jspobfuscator.api.page.node.context;

import java.util.Stack;

public interface RenderContext {

  String render();

  RenderContext append(String s);

  Stack<String> stack();

  String pop();

  RenderContext push(String endTag);

  // clear output buffer
  void clearBuffer();

  // clear stack
  void clearStack();
}
