package com.t3stzer0.jspobfuscator.api.page.node.context;

import java.util.Stack;

public abstract class AbstractRenderContext implements RenderContext {
  protected Stack<String> stack;
  protected StringBuilder outputBuffer;

  @Override
  public Stack<String> stack() {
    return stack;
  }

  @Override
  public String pop() {
    return stack.pop();
  }

  @Override
  public RenderContext push(String tag) {
    stack.push(tag);
    return this;
  }

  @Override
  public String render() {
    return outputBuffer.toString();
  }

  @Override
  public RenderContext append(String str) {
    outputBuffer.append(str);
    return this;
  }

  @Override
  public void clearBuffer() {
    outputBuffer.setLength(0);
  }

  @Override
  public void clearStack() {
    stack.clear();
  }
}
