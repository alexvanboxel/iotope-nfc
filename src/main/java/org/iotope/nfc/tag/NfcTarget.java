package org.iotope.nfc.tag;

import org.iotope.nfc.reader.pn532.struct.PN532TargetData;
import org.iotope.util.IOUtil;

public class NfcTarget {
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((targetData == null) ? 0 : targetData.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NfcTarget other = (NfcTarget) obj;
        if (targetData == null) {
            if (other.targetData != null)
                return false;
        } else if (!targetData.equals(other.targetData))
            return false;
        return true;
    }

    public NfcTarget(PN532TargetData targetData) {
        this.targetData = targetData;
    }
    
    public byte[] getNfcId() {
        return targetData.getNfc1Id();
    }
    
    public TagType getType() {
        return targetData.getType();
    }
    
    @Override
    public String toString() {
        return targetData.getType() + " " + IOUtil.hex(getNfcId());
    }

    protected PN532TargetData targetData;

    public boolean isDEP() {
        return false;
    }
}
