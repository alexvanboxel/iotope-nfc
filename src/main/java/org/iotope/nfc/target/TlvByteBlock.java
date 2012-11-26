package org.iotope.nfc.target;

import org.iotope.nfc.target.NfcTlv.ContentType;
import org.iotope.util.IOUtil;

public class TlvByteBlock extends TlvBlock {
    private byte[] content;
    
    TlvByteBlock(ContentType type, byte[] content) {
        this.type = type;
        this.content = content;
    }
    
    public byte[] getBlock() {
        return content;
    }

    @Override
    public String toString() {
        return type.name() + ": " + IOUtil.hex(content);
    }

}