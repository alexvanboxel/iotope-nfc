package org.iotope.nfc.reader.pn532.struct;

import java.nio.ByteBuffer;

import org.iotope.nfc.tag.NfcTarget;
import org.iotope.nfc.tag.TagTechType;

public abstract class PN532TargetData {
    
    public PN532TargetData(ByteBuffer buffer, int len) {
        mark = buffer.position();
        length = len;
        target = buffer.get();
    }
    
    protected byte[] remaining(ByteBuffer buffer) {
        int len = length - (buffer.position() - mark);
        if(len > 0) {
            remaining = new byte[len];
            buffer.get(remaining);
            return remaining;
        }
        return null;
    }

    abstract public byte[] getNfc1Id();
    
    public byte[] getNfc3Id() {
        return null;
    }

    public byte getTarget() {
        return target;
    }
    
    abstract public TagTechType getType();
    
    abstract public NfcTarget createNfcTarget();
    
    private int mark;
    private int length;
    private byte target;
    private byte[] remaining;
}
