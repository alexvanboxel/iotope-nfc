package org.iotope.nfc.tag;

import org.iotope.nfc.reader.pn532.struct.PN532TargetData;
import org.iotope.util.IOUtil;

public class NfcTag {
    
    public NfcTag(PN532TargetData targetData) {
        this.targetData = targetData;
    }
    
    public byte getNfcIdLength() {
        return targetData.getNfcIdLength();
    }
    
    public byte[] getNfcId() {
        return targetData.getNfcId();
    }
    
    public TagType getType() {
        return targetData.getType();
    }
    
    @Override
    public String toString() {
        return targetData.getType() + " " + IOUtil.hex(getNfcId());
    }

    protected PN532TargetData targetData;
}
