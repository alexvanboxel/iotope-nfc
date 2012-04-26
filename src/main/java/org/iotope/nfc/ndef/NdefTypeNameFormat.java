package org.iotope.nfc.ndef;

/**
 * TS NDEF 1.0 3.2.6 TNF (Type Name Format) page 16
 */
public enum NdefTypeNameFormat {
    Empty(0x00), //
    WellKnownType(0x01), // NFC Forum well-known type [NFC RTD]
    MediaType(0x02), // Media-type as defined in RFC 2046 [RFC 2046]
    AbsoluteUri(0x03), // Absolute URI as defined in RFC 3986 [RFC 3986]
    ExternalType(0x04), // NFC Forum external type [NFC RTD]
    Unkown(0x05), //
    Unchanged(0x06), // Unchanged (see section 2.3.3)
    Reserved(0x07); //
    
    
    private NdefTypeNameFormat(int code) {
        this.code = code;
    }
    
    private int code;

    public static NdefTypeNameFormat fromNdefHead(byte head) {
        int codeInHead = head & 0x07;
        for(NdefTypeNameFormat tnf : values()) {
            if(tnf.getCode() == codeInHead) {
                return tnf;
            }
        }
        return null;
    }

    public  int getCode() {
        return code;
    }
}
