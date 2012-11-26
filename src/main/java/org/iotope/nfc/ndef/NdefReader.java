package org.iotope.nfc.ndef;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;

public class NdefReader {
    
    
    static void parseMessageFromByteArray(byte[] buffer) {
        
    }
    
    //	static void parseMessageFromNdefMessage(NdefParsedMessage message) {
    //		NdefParsedRecord[] records = message.getRecords();
    //		for(NdefParsedRecord record : records) {
    //			
    //		}
    //	}
    
    
    public NdefReader(byte[] ndefBuffer) {
        this.ndefBuffer = ByteStreams.newDataInput(ndefBuffer);
    }
    
    /**
     * 7 6 5 4 3 2 1 0 MB ME CF SR IL -TNF- TYPE LENGTH PAYLOAD LENGTH ID LENGTH
     * TYPE ID PAYLOAD
     * 
     * @return
     * @throws IOException
     */
    public NdefParsedMessage parse() throws IOException {
        NdefParsedMessage ndefMessage = new NdefParsedMessage();
        boolean readMessage = true;
        while (readMessage) {
            byte head = ndefBuffer.readByte();
            // NDEF header
            NdefTypeNameFormat tnf = NdefTypeNameFormat.fromNdefHead(head);
            boolean mb = ((head >> 7) & 1) == 1;
            boolean me = ((head >> 6) & 1) == 1;
            boolean cf = ((head >> 5) & 1) == 1;
            boolean sr = ((head >> 4) & 1) == 1;
            boolean il = ((head >> 3) & 1) == 1;
            byte typeLength = ndefBuffer.readByte();
            int payloadLength;
            if (sr) {
                payloadLength = ndefBuffer.readByte();
            } else {
                payloadLength = ndefBuffer.readInt();
            }
            byte idLength = 0;
            if (il) {
                idLength = ndefBuffer.readByte();
            }
            // Actual payload
            byte[] typeBuffer = new byte[typeLength];
            ndefBuffer.readFully(typeBuffer);
            String typeName = "urn:nfc:wkt:" + new String(typeBuffer, Charsets.UTF_8);
            byte[] idBuffer = null;
            if (idLength > 0) {
                idBuffer = new byte[idLength];
                ndefBuffer.readFully(idBuffer);
            }
            byte[] payloadBuffer = new byte[payloadLength];
            ndefBuffer.readFully(payloadBuffer);
            if ("urn:nfc:wkt:U".equals(typeName)) {
                ndefMessage.add(new NdefRTDURI(idBuffer, payloadBuffer));
            } else if ("urn:nfc:wkt:T".equals(typeName)) {
                ndefMessage.add(new NdefRTDText(idBuffer, payloadBuffer));
            } else if ("U".equals(typeName)) {
            }
            if (me) {
                readMessage = false;
            }
        }
        return ndefMessage;
    }
    
    DataInput ndefBuffer;
}
