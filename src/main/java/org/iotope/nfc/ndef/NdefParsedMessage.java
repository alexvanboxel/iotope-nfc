package org.iotope.nfc.ndef;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iotope.nfc.tech.DataIO;

import com.google.common.base.Charsets;

public class NdefParsedMessage implements DataIO {
    
    static final Map<String, Class<? extends NdefParsedRecord>> parsableWellKnown;
    static {
        parsableWellKnown = new HashMap<String, Class<? extends NdefParsedRecord>>();
        parsableWellKnown.put(NdefRTDURI.TYPE, NdefRTDURI.class);
    }
    
    public void add(NdefParsedRecord record) {
        records.add(record);
    }
    
    private List<NdefParsedRecord> records = new ArrayList<NdefParsedRecord>();
    
    public int size() {
        return records.size();
    }
    
    public NdefParsedRecord getRecord(int ix) {
        return records.get(ix);
    }
    
    public List<NdefParsedRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }


    @Override
    public void read(DataInput ndefBuffer) throws IOException {
        int messageCount = 0;
        for (boolean readMessage = true;readMessage;messageCount++) {
            byte head = ndefBuffer.readByte();
            // NDEF header
            NdefTypeNameFormat tnf = NdefTypeNameFormat.fromNdefHead(head);
            boolean flagMessageBegin = ((head >> 7) & 1) == 1;
            boolean flagMessageEnd = ((head >> 6) & 1) == 1;
            boolean flagChunkFlag = ((head >> 5) & 1) == 1;
            boolean flagShortRecord = ((head >> 4) & 1) == 1;
            boolean flagIdPresent = ((head >> 3) & 1) == 1;
            byte typeLength = ndefBuffer.readByte();
            if(flagMessageBegin && messageCount != 0) {
                throw new IOException("Format incorrect: flagMessageBegin not at beginning");
            }
            int payloadLength;
            if (flagShortRecord) {
                payloadLength = ndefBuffer.readByte();
            } else {
                payloadLength = ndefBuffer.readInt();
            }
            byte idLength = 0;
            if (flagIdPresent) {
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
                add(new NdefRTDURI(idBuffer,payloadBuffer));
            } else if ("U".equals(typeName)) {
            }
            if (flagMessageEnd) {
                readMessage = false;
            }
        }
    }

    @Override
    public void write(DataOutput output) throws IOException {
        int messageCount = records.size();
        for(int ix=0;ix<messageCount;ix++) {
            NdefParsedRecord record = records.get(ix);
            
            boolean flagMessageBegin = false;
            boolean flagMessageEnd = false;
            boolean flagChunkFlag = false;
            boolean flagShortRecord = false;
            boolean flagIdPresent = false;
            // Start/End flags
            if(ix == 0) {
                flagMessageBegin = true;
            }
            if(ix == messageCount-1) {
                flagMessageEnd = true;
            }

            // NDEF header
            NdefTypeNameFormat tnf = record.getTypeNameFormat();
            byte[] type = record.getType();

            // Payload
            byte[] payload = record.getPayload();
            int payloadLength = payload.length;
            flagShortRecord = payloadLength < Byte.MAX_VALUE-1;

            // Id present flag
            byte[] id = record.getIdAsByteArray();
            if(id != null) {
                flagIdPresent = true;
            }

            // Write header
            int head = tnf.getCode();
            head |= (flagMessageBegin ? 1 : 0) << 7;
            head |= (flagMessageEnd ? 1 : 0) << 6;
            head |= (flagChunkFlag ? 1 : 0) << 5;
            head |= (flagShortRecord ? 1 : 0) << 4;
            head |= (flagIdPresent ? 1 : 0) << 3;
            
            // Type Length
            output.writeByte(type.length);
            
            // Payload Length
            if (flagShortRecord) {
                output.writeByte(payloadLength);
            } else {
                output.writeInt(payloadLength);
            }
            
            // Id Length
            if (flagIdPresent) {
                output.writeByte(id.length);
            }
            
            // Write Type
            output.write(type);
            
            // Write Id
            if(flagIdPresent) {
                output.write(id);
            }
            
            // Write Payload
            output.write(payload);
        }
    }
    
}
