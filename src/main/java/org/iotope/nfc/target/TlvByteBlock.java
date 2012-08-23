package org.iotope.nfc.target;

import org.iotope.nfc.target.NfcTlv.ContentType;

public class TlvByteBlock extends TlvBlock {
    private byte[] content;
    
    TlvByteBlock(ContentType type, byte[] content) {
        this.type = type;
        this.content = content;
    }
    
    public byte[] getBlock() {
        return content;
    }
}