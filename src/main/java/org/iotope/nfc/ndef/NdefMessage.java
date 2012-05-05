package org.iotope.nfc.ndef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NdefMessage {
    
    
    public void add(NdefRecord record) {
        records.add(record);
    }
    
    private List<NdefRecord> records = new ArrayList<NdefRecord>();
    
    public int size() {
        return records.size();
    }
    
    public NdefRecord getRecord(int ix) {
        return records.get(ix);
    }
    
    public List<NdefRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }
    
}
