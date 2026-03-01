package com.t3stzer0.jspobfuscator.core.ui.controller;

import com.t3stzer0.jspobfuscator.api.page.Page;
import com.t3stzer0.jspobfuscator.api.page.node.*;
import com.t3stzer0.jspobfuscator.api.page.node.PageNode;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class EditorController {
  public static final String CODE_SPLIT_REGEX = "\\R//={3,}\\R";
  public static final String CODE_SPLIT_STRING = "\n//==================================\n";

  public enum WebViewName {
    CODE,
    INCLUDE,
    DECLARATION
  }

  public static HashMap<Character, Integer> hex2int =
      new HashMap<Character, Integer>() {
        {
          put('0', 0);
          put('1', 1);
          put('2', 2);
          put('3', 3);
          put('4', 4);
          put('5', 5);
          put('6', 6);
          put('7', 7);
          put('8', 8);
          put('9', 9);
          put('a', 10);
          put('b', 11);
          put('c', 12);
          put('d', 13);
          put('e', 14);
          put('f', 15);
          put('A', 10);
          put('B', 11);
          put('C', 12);
          put('D', 13);
          put('E', 14);
          put('F', 15);
        }
      };
  //	public Page InputPage = new Page();
  MainController mainController;
  String unrepresentableCharacter = "\uFFFD";

  public EditorController(MainController controller) {
    mainController = controller;
    mainController
        .getWebViewInputCode()
        .getEngine()
        .load(this.getClass().getResource("/ui/templates/editor/editor_java.html").toExternalForm());
    mainController
        .getWebViewInputInclude()
        .getEngine()
        .load(this.getClass().getResource("/ui/templates/editor/editor_java.html").toExternalForm());
    mainController
        .getInputDeclaration()
        .getEngine()
        .load(this.getClass().getResource("/ui/templates/editor/editor_java.html").toExternalForm());
    mainController
        .getWebViewOutputText()
        .getEngine()
        .load(this.getClass().getResource("/ui/templates/editor/editor_java.html").toExternalForm());
    mainController
        .getWebViewOutputHex()
        .getEngine()
        .load(this.getClass().getResource("/ui/templates/editor/editor_hex.html").toExternalForm());
    //		bindListener();
  }

  public static String hexToString(String hexString) throws Exception {
    hexString = hexString.replace(" ", "");
    if (hexString.length() % 2 != 0) {
      throw new Exception("hex string error!");
    }
    byte[] bytes = new byte[hexString.length() / 2];
    for (int i = 0; i < hexString.length(); i += 2) {
      byte c =
          (byte) ((hex2int.get(hexString.charAt(i)) << 4) + hex2int.get(hexString.charAt(i + 1)));
      bytes[i / 2] = c;
    }
    return new String(bytes, StandardCharsets.UTF_8);
  }

  public void setOutput(byte[] bytes) {

    String b64String = Base64.getEncoder().encodeToString(bytes);
    mainController
        .getWebViewOutputText()
        .getEngine()
        .executeScript("editor.setValue(atob(\"" + b64String + "\"), -1)");

    String hex = bytesToHexString(bytes);
    mainController
        .getWebViewOutputHex()
        .getEngine()
        .executeScript("setInputValue(String.raw`" + hex + "`, -1)");
  }

  public String bytesToHexString(byte[] bytes) {
    StringBuilder sb = new StringBuilder(bytes.length * 2);

    for (byte aByte : bytes) {
      sb.append(String.format("%02x", Byte.toUnsignedInt(aByte)));
    }
    return sb.toString();
  }

  public void setWebViewTextByName(WebViewName name, List<String> text) {
    switch (name) {
      case INCLUDE:
        {
          String concatString = String.join("\n", text);
          mainController
              .getWebViewInputInclude()
              .getEngine()
              .executeScript("editor.setValue(String.raw`" + concatString + "`, -1)");
          break;
        }
      case CODE:
        {
          String concatString = String.join(CODE_SPLIT_STRING, text);
          mainController
              .getWebViewInputCode()
              .getEngine()
              .executeScript("editor.setValue(String.raw`" + concatString + "`, -1)");
          break;
        }
      case DECLARATION:
        {
          String concatString = String.join(CODE_SPLIT_STRING, text);
          mainController
              .getInputDeclaration()
              .getEngine()
              .executeScript("editor.setValue(String.raw`" + concatString + "`, -1)");
          break;
        }
    }
  }

  public String getWebViewTextByName(WebViewName name) {
    return (switch (name) {
          case CODE ->
              (String)
                  mainController
                      .getWebViewInputCode()
                      .getEngine()
                      .executeScript("editor.getValue()");
          case INCLUDE ->
              (String)
                  mainController
                      .getWebViewInputInclude()
                      .getEngine()
                      .executeScript("editor.getValue()");
          case DECLARATION ->
              (String)
                  mainController
                      .getInputDeclaration()
                      .getEngine()
                      .executeScript("editor.getValue()");
        })
        .strip();
  }

  public List<String> getCode() {
    String rawText = getWebViewTextByName(WebViewName.CODE);
    if (rawText.isEmpty()) {
      return List.of();
    }
    return Arrays.stream(rawText.split(CODE_SPLIT_REGEX)).toList();
  }

  public List<String> getDeclaration() {
    String rawText = getWebViewTextByName(WebViewName.DECLARATION);
    if (rawText.isEmpty()) {
      return List.of();
    }
    return Arrays.stream(rawText.split(CODE_SPLIT_REGEX)).toList();
  }

  public List<String> getImports() {
    String imports = getWebViewTextByName(WebViewName.INCLUDE);
    if (imports.isEmpty()) {
      return List.of();
    }
    return Arrays.stream(imports.split("\\R+"))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .collect(Collectors.toList());
  }
}
