package org.iotope.nfc.tech.mifare;

import java.nio.ByteBuffer;

import org.iotope.nfc.reader.ReaderTransmit;

public class MifareReadBlock implements ReaderTransmit {
    
    public MifareReadBlock(int block) {
        super();
        this.block = block;
    }

    @Override
    public int getLength() {
        return 2;
    }

    @Override
    public void transfer(ByteBuffer buffer) {
        buffer.put((byte)0x30);
        buffer.put((byte)block);
    }
    
    private int block;
}
