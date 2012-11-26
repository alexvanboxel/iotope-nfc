package org.iotope.nfc.ndef;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class NdefRTDText extends NdefParsedRecord {

	public static final String TYPE = "urn:nfc:wkt:T";
	
	private int status;
	private String languageCode;
	private String text;

	public NdefRTDText(byte[] id,byte[] payload) {
        super(id,payload);
        status = (int) payload[0];
        int langLength = status & 0x1F;
        boolean utf16 = ((status & 0x40) >> 7) == 0x01;
        if(langLength > 0) {
            try {
                languageCode =  new String(Arrays.copyOfRange(payload, 1, 1+langLength),"US-ASCII");
                text = new String(Arrays.copyOfRange(payload, 1+langLength,payload.length), utf16 ? "UTF-16" : "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int getLength() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getContent() {
        // TODO Auto-generated method stub
        return "";
    }
    
    public String getRTD() {
        return "urn:nfc:wkt:T";
    }
    

    public NdefTypeNameFormat getTypeNameFormat() {
        return NdefTypeNameFormat.WellKnownType;
    }
    
    @Override
    public byte[] getType() {
        return new byte[] { 'T' };
    }


    @Override
    public String toString() {
        return "RTD: "+getRTD()+", language:" + languageCode +", text:"+text;
    }
}


