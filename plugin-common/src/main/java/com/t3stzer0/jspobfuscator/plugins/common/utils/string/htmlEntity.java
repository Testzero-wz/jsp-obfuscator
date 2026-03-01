package com.t3stzer0.jspobfuscator.plugins.common.utils.string;

public class htmlEntity {
    public static String encodeHex(char c, int zeroRepeatTimes) {
        return "&#x" + "0".repeat(zeroRepeatTimes) + String.format("%x;", (int) c);
    }

    public static String encodeDecimal(char c, int zeroRepeatTimes) {
        return "&#" + "0".repeat(zeroRepeatTimes) + String.format("%d;", (int) c);
    }
}
