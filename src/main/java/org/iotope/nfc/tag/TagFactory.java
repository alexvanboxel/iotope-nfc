package org.iotope.nfc.tag;

import java.nio.ByteBuffer;

import org.iotope.nfc.reader.pn532.struct.DATA_EXCHANGE_PROTOCOL;
import org.iotope.nfc.reader.pn532.struct.ISO14443_A_106KBPS;
import org.iotope.nfc.reader.pn532.struct.PN532TargetData;
import org.iotope.nfc.reader.pn532.struct.RAW_TARGETDATA;

public class TagFactory {
    
    public static PN532TargetData build(ByteBuffer buffer) {
        byte type = buffer.get();
        int len = buffer.get();
        switch (type) {
        case 0x10:
            return new ISO14443_A_106KBPS(buffer, len);
        case 0x40:
            return new DATA_EXCHANGE_PROTOCOL(buffer, len);
        default:
            return new RAW_TARGETDATA(buffer, len);
        }
    }
    
    public NfcTarget createNfcTag() {
        if (iso != null) {
            if (iso instanceof ISO14443_A_106KBPS) {
                switch (iso.getType()) {
                case MIFARE_1K:
                    return new MifareClassic((ISO14443_A_106KBPS) iso);
                case MIFARE_ULTRALIGHT:
                    return new MifareUltraLight((ISO14443_A_106KBPS) iso);
                }
            } else if (iso instanceof DATA_EXCHANGE_PROTOCOL) {
                return new DataExchangeProtocol((DATA_EXCHANGE_PROTOCOL) iso);
            }
        }
        return null;
    }
    
    private PN532TargetData iso;
}
