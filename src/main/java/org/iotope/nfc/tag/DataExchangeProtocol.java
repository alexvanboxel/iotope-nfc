package org.iotope.nfc.tag;

import org.iotope.nfc.reader.pn532.struct.PN532TargetData;

public class DataExchangeProtocol extends NfcTarget {

    public DataExchangeProtocol(PN532TargetData targetData) {
        super(targetData);
    }
 

    public boolean isDEP() {
        return true;
    }

}
