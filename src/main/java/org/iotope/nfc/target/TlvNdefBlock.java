package org.iotope.nfc.target;

import org.iotope.nfc.ndef.NdefParsedMessage;
import org.iotope.nfc.target.NfcTlv.ContentType;

public class TlvNdefBlock extends TlvBlock {
    private NdefParsedMessage ndef;
    
    TlvNdefBlock(ContentType type, NdefParsedMessage content) {
        this.type = type;
        this.ndef = content;
    }
    
    public NdefParsedMessage getNdef() {
        return ndef;
    }
    
}