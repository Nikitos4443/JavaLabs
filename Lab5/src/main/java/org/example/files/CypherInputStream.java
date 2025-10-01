package org.example.files;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CypherInputStream extends FilterInputStream {

    private final byte key;
    private final boolean isEncrypt;

    public CypherInputStream(InputStream in, byte key, boolean isEncrypt) {
        super(in);
        this.key = key;
        this.isEncrypt = isEncrypt;
    }

    @Override
    public int read() throws IOException {
        int result = super.read();
        if (result == (int) '\n') return -1;

        if(isEncrypt) return (result + key) & 0xFF;

        return (result - key) & 0xFF;
    }
}
