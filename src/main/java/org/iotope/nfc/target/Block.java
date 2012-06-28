package org.iotope.nfc.target;

import org.iotope.nfc.target.TargetContent.ContentType;

public class Block {
    protected ContentType type;
    
    public Block() {
    }
    
    public ContentType getType() {
        return type;
    }
}