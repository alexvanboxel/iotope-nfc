package org.iotope.nfc.target;

import org.iotope.nfc.target.NfcTlv.ContentType;

public class TlvBlock {
    protected ContentType type;
    
    public TlvBlock() {
    }
    
    public ContentType getType() {
        return type;
    }
}