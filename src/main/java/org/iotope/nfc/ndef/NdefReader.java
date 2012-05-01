package org.iotope.nfc.ndef;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;


public class NdefReader {

    public NdefReader(byte[] ndefBuffer) {
        this.ndefBuffer = ByteBuffer.wrap(ndefBuffer);
    }

    /**
     * 7  6  5  4  3  2 1 0 
     * MB ME CF SR IL -TNF-
     * TYPE LENGTH
     * PAYLOAD LENGTH
     * ID LENGTH
     * TYPE
     * ID
     * PAYLOAD
     * @return
     */
    public NdefMessage parse() {
        NdefMessage ndefMessage = new NdefMessage();
        boolean readMessage = true;
        while(readMessage) {
            byte head = ndefBuffer.get();
            byte[] tmp;
            NdefTypeNameFormat tnf = NdefTypeNameFormat.fromNdefHead(head);
            boolean mb = ((head >> 7) & 1) == 1;
            boolean me = ((head >> 6) & 1) == 1;
            boolean cf = ((head >> 5) & 1) == 1;
            boolean sr = ((head >> 4) & 1) == 1;
            boolean il = ((head >> 3) & 1) == 1;
            byte typeLength = ndefBuffer.get();
            int payloadLength;
            if(sr) {
                payloadLength = ndefBuffer.get();
            }
            else {
                payloadLength = ndefBuffer.getInt();
            }
            byte idLength = 0;
            if(il) {
                idLength = ndefBuffer.get();
            }
            tmp = new byte[typeLength];
            ndefBuffer.get(tmp);
            String typeName = new String(tmp,Charset.forName("UTF-8"));
            if(idLength > 0) {
                tmp = new byte[idLength];
                ndefBuffer.get(tmp);
            }
            tmp = new byte[payloadLength];
            ndefBuffer.get(tmp);
            if("U".equals(typeName)) {
                ndefMessage.add(new NdefRTDURI(tmp));
            }
            else if("U".equals(typeName)) {
            }
            if(me) {
                readMessage = false;
            }
        }
        return ndefMessage;
    }
    
    ByteBuffer ndefBuffer;
    
    
}
