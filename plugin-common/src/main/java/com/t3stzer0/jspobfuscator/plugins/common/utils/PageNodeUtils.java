package com.t3stzer0.jspobfuscator.plugins.common.utils;

import com.t3stzer0.jspobfuscator.api.page.node.PageNode;

import java.util.stream.Collectors;

public class PageNodeUtils {
  public static String outputAttributes(PageNode node) {
    return node.getAttributes().entrySet().stream()
        .map(entry -> entry.getKey() + "=\"" + entry.getValue() + "\"")
        .collect(Collectors.joining(" "));
  }
}
