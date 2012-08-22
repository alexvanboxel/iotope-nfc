package org.iotope.nfc.ndef;

public class NdefRTDText extends NdefParsedRecord {

	public static final String TYPE = "urn:nfc:wkt:T";

	public NdefRTDText(byte[] id,byte[] payload) {
        super(id,payload);
    }

    @Override
    public int getLength() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getContent() {
        // TODO Auto-generated method stub
        return "";
    }
    
    public String getRTD() {
        return "urn:nfc:wkt:T";
    }
}
