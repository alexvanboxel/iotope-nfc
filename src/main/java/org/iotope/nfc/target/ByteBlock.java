package org.iotope.nfc.target;

import org.iotope.nfc.target.TargetContent.ContentType;

public class ByteBlock extends Block {
    private byte[] content;
    
    ByteBlock(ContentType type, byte[] content) {
        this.type = type;
        this.content = content;
    }
    
    public byte[] getBlock() {
        return content;
    }
}