package org.iotope.nfc.target;

import org.iotope.nfc.ndef.NdefMessage;
import org.iotope.nfc.target.TargetContent.ContentType;

public class NdefBlock extends Block {
    private NdefMessage ndef;
    
    NdefBlock(ContentType type, NdefMessage content) {
        this.type = type;
        this.ndef = content;
    }
    
    public NdefMessage getNdef() {
        return ndef;
    }
    
}