package org.iotope.nfc.ndef;

public class NdefRTDText extends NdefRecord {

    public NdefRTDText(byte[] payload) {
        super(payload);
        // TODO Auto-generated constructor stub
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
