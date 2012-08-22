package org.iotope.nfc.ndef;

public abstract class NdefParsedRecord {
    
    public NdefParsedRecord(byte[] id,byte[] payload) {
        this.payload = payload;
    }

    abstract public int getLength();
    
    abstract public String getId();

    abstract public String getContent();
    
    abstract public String getRTD();
    
    protected byte[] id;
    
    protected byte[] payload;
    
    public byte[] getPayload() {
        return payload;
    }
    
    public Object getRepresentation() {
        return getPayload();
    }
}
