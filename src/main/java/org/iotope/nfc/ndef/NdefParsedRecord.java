package org.iotope.nfc.ndef;

public abstract class NdefParsedRecord {
    
    public NdefParsedRecord(byte[] id, byte[] payload) {
        this.payload = payload;
    }
    
    abstract public int getLength();
    
    public final String getId() {
        return null;
    }
    
    public final byte[] getIdAsByteArray() {
        return this.id;
    }
    
    
    abstract public String getContent();
    
    abstract public String getRTD();
    
    abstract public byte[] getType();
    
    private byte[] id;
    
    protected byte[] payload;
    
    
    public byte[] getPayload() {
        return payload;
    }
    
    public Object getRepresentation() {
        return getPayload();
    }
    
    abstract NdefTypeNameFormat getTypeNameFormat();
}
