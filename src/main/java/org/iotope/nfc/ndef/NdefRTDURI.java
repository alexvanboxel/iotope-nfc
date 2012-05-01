package org.iotope.nfc.ndef;

import java.net.URI;
import java.nio.charset.Charset;

import com.google.common.collect.ImmutableBiMap;

public class NdefRTDURI extends NdefRecord {
    
    public NdefRTDURI(byte[] payload) {
        this.payload = payload;
        int identifier = (int)payload[0];
        String post = abbreviation.get(identifier);
        uri = URI.create(post + new String(payload,1,payload.length-1,Charset.forName("UTF-8")));
    }

    @Override
    public int getLength() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public String getId() {
        return null;
    }
    
    static {
        ImmutableBiMap.Builder<Integer, String> builder = ImmutableBiMap.builder(); //
        builder.put(0x00, "");
        builder.put(0x01, "http://www.");
        builder.put(0x02, "https://www.");
        builder.put(0x03, "http://");
        builder.put(0x04, "https://");
        builder.put(0x05, "tel:");
        builder.put(0x06, "mailto:");
        builder.put(0x07, "ftp://anonymous:anonymous@");
        builder.put(0x08, "ftp://ftp.");
        builder.put(0x09, "ftps://");        
        builder.put(0x0A, "sftp://");
        builder.put(0x0B, "smb://");
        builder.put(0x0C, "nfs://");
        builder.put(0x0D, "ftp://");
        builder.put(0x0E, "dav://");
        builder.put(0x0F, "news:");
        builder.put(0x10, "telnet://");
        builder.put(0x11, "imap:");
        builder.put(0x12, "rtsp://");
        builder.put(0x13, "urn:");
        builder.put(0x14, "pop:");
        builder.put(0x15, "sip:");
        builder.put(0x16, "sips:");
        builder.put(0x17, "tftp:");
        builder.put(0x18, "btspp://");
        builder.put(0x19, "btl2cap://");
        builder.put(0x1A, "btgoep://");
        builder.put(0x1B, "tcpobex://");
        builder.put(0x1C, "irdaobex://");
        builder.put(0x1D, "file://");
        builder.put(0x1E, "urn:epc:id:");
        builder.put(0x1F, "urn:epc:tag:");
        builder.put(0x20, "urn:epc:pat:");
        builder.put(0x21, "urn:epc:raw:");
        builder.put(0x22, "urn:epc:");
        builder.put(0x23, "urn:nfc:");
        
        abbreviation = builder.build();
    }
    
    /**
     * TS RTD URI Table 3. Abbreviation Table (page 5)
     */
    private static ImmutableBiMap<Integer, String> abbreviation;
    
    private byte[] payload;
    
    private URI uri;

    public URI getURI() {
        return uri;
    }
}
