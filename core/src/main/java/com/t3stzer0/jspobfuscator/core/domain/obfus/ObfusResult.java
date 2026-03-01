package com.t3stzer0.jspobfuscator.core.domain.obfus;

public class ObfusResult {
    private final byte[] data;
    private final Class<?> pageClass;

    public ObfusResult(byte[] data, Class<?> pageClass) {
        this.data = data;
        this.pageClass = pageClass;
    }

    public byte[] getData() {
        return data;
    }
    
    public Class<?> getPageClass() {
        return pageClass;
    }
}
