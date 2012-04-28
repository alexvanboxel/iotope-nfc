package org.iotope.nfc.reader.pn532.struct;

import java.nio.ByteBuffer;

import org.iotope.nfc.tag.DataExchangeProtocol;
import org.iotope.nfc.tag.NfcTarget;
import org.iotope.nfc.tag.TagType;


public class RAW_TARGETDATA extends PN532TargetData {
    
    public RAW_TARGETDATA(ByteBuffer buffer, int len) {
        super(buffer, len);
        remaining(buffer);
    }
    
    public byte[] getNfc1Id() {
        return null;
    }
    
    public byte[] getNfc3Id() {
        return null;
    }
    
    public TagType getType() {
        return TagType.RAW;
    }
    
    @Override
    public NfcTarget createNfcTarget() {
        return new DataExchangeProtocol(this);
    }
    
}
