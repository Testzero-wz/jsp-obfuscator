package com.t3stzer0.jspobfuscator.plugins.common.utils.reader;

import java.io.IOException;

public class StringReader {

  private final java.io.StringReader reader;

  public StringReader(String buffer) {
    this.reader = new java.io.StringReader(buffer);
  }

  /*
  return -1 if EOF
   */
  public int peekOne() {
    try {
      reader.mark(1);
      int ret = reader.read();
      reader.reset();
      return ret;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public long skip(long n) {
    try {
      return reader.skip(n);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /*
  pos = EOF or sep_pos - 1
   */
  public String readUntilOrEnd(char sep) {
    StringBuilder sb = new StringBuilder();
    int c;
    while ((c = peekOne()) != -1 && c != sep) {
      sb.append((char) c);
      skip(1);
    }
    return sb.toString();
  }

  public boolean isReachEOF() {
    return peekOne() == -1;
  }
}
