package org.iotope.nfc.tech;

import java.io.DataInput;
import java.io.IOException;

public interface DataI {
    
    void read(DataInput input) throws IOException;
}
