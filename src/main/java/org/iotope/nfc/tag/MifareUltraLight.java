package org.iotope.nfc.tag;

import org.iotope.nfc.reader.pn532.struct.ISO14443_A_106KBPS;

public class MifareUltraLight extends NfcTarget {

    public MifareUltraLight(ISO14443_A_106KBPS iso) {
        super(iso);
    }
    
}
