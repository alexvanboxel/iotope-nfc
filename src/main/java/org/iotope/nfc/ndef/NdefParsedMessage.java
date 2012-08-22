package org.iotope.nfc.ndef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NdefParsedMessage {
    
    
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
    
}
