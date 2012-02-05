package org.iotope.nfc.reader;

import java.nio.ByteBuffer;

public interface ReaderCommand<COMMAND,RESPONSE> {

    int getLength();
    
    void transfer(ByteBuffer buffer);

    int getInstruction();

}
