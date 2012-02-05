package org.iotope.nfc.reader;

import java.nio.ByteBuffer;

public abstract class AbstractCommand<COMMAND, RESPONSE> implements ReaderCommand<COMMAND, RESPONSE> {
    
    Class<RESPONSE> responseClass;
    
    public AbstractCommand(Class<RESPONSE> responseClass) {
        this.responseClass = responseClass;
    }
        
    public RESPONSE transmit(ReaderChannel channel) {
        return null;
    }
    public int getLength() {return 0;}
    // public
    public void transfer(ByteBuffer buffer) {}
    
}
