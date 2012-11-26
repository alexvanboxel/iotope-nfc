package org.iotope.nfc.tech;

import java.io.DataOutput;
import java.io.IOException;

public interface DataO {
    
    void write(DataOutput output) throws IOException;
    
}
