package org.iotope.nfc.tag;

import org.iotope.nfc.reader.pn532.struct.ISO14443_A_106KBPS;

public class TagFactory {
    
    public TagFactory(int type, byte[] targetData) {
        iso = new ISO14443_A_106KBPS(targetData);
        System.out.println(iso.getType());
    }
    
    public void t() {
        
        
    }
    
    public NfcTag createNfcTag() {
        switch(iso.getType()) {
        case MIFARE_1K:
            return new MifareClassic(iso);
        case MIFARE_ULTRALIGHT:
            return new MifareUltraLight(iso);
        }
        
        // TODO Auto-generated method stub
        return null;
    }
    
    private ISO14443_A_106KBPS iso;
}
