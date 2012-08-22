package org.iotope.nfc.target;

import org.iotope.nfc.ndef.NdefParsedMessage;
import org.iotope.nfc.target.TargetContent.ContentType;

public class NdefBlock extends Block {
    private NdefParsedMessage ndef;
    
    NdefBlock(ContentType type, NdefParsedMessage content) {
        this.type = type;
        this.ndef = content;
    }
    
    public NdefParsedMessage getNdef() {
        return ndef;
    }
    
}