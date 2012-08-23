package org.iotope.nfc.tech;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface DataIO {
    
    void read(DataInput input) throws IOException;
    
    void write(DataOutput output) throws IOException;
    
}
