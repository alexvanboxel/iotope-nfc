package org.iotope.nfc.reader.pn532.struct;

import org.iotope.nfc.tag.TagType;

public abstract class PN532TargetData {
    
    abstract public byte getNfcIdLength();
    
    abstract public byte[] getNfcId();
    
    abstract public TagType getType();
    
}
