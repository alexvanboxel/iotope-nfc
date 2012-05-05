package org.iotope.nfc.ndef;

public abstract class NdefRecord {
    
    public NdefRecord(byte[] payload) {
        this.payload = payload;
    }

    abstract public int getLength();
    
    abstract public String getId();

    abstract public String getContent();
    
    protected byte[] payload;
    
    public byte[] getPayload() {
        return payload;
    }
    
    public Object getRepresentation() {
        return getPayload();
    }
}
