package org.iotope.nfc.ndef;

import java.util.ArrayList;
import java.util.List;

public class NdefMessage {

    
    public void add(NdefRecord record) {
        records.add(record);
    }
    
    private List<NdefRecord> records = new ArrayList<NdefRecord>();
}
