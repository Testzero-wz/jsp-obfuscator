package com.t3stzer0.jspobfuscator.plugins.common.utils.string;

import com.t3stzer0.jspobfuscator.plugins.common.utils.reader.StringReader;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class replace {
  public static String ReplaceWithFunc(
      String data, String regex, Function<MatchResult, String> func) {
    return Pattern.compile(regex).matcher(data).replaceAll(func);
  }

  public static String ReplaceQuoteStringWithFunc(String data, Function<String, String> func) {
    StringReader reader = new StringReader(data);
    StringBuilder sb = new StringBuilder();
    char sep = '"';
    String tmp;
    while (!reader.isReachEOF()) {
      tmp = reader.readUntilOrEnd(sep);
      sb.append(tmp);
      if (reader.isReachEOF()) {
        break;
      }
      reader.skip(1);
      tmp = reader.readUntilOrEnd(sep);
      if (reader.isReachEOF()) {
        throw new IllegalArgumentException("Unclosed quote string");
      }
      reader.skip(1);
      sb.append(func.apply(tmp));
    }
    return sb.toString();
  }
}
