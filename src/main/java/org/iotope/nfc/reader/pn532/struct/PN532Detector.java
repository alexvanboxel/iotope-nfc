package org.iotope.nfc.reader.pn532.struct;


public abstract class PN532Detector {
    
    abstract public PN532TargetData detect(byte[] targetData);
    
}
