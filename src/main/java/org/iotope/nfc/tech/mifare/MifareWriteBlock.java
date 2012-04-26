package org.iotope.nfc.tech.mifare;

import java.nio.ByteBuffer;

import org.iotope.nfc.reader.ReaderTransmit;

public class MifareWriteBlock implements ReaderTransmit{
    
    public MifareWriteBlock(int block, byte[] data) {
        super();
        this.block = block;
        this.data = data;
    }

    @Override
    public int getLength() {
        return 2+data.length;
    }  

    @Override
    public void transfer(ByteBuffer buffer) {
        if (data.length == 4) {
            buffer.put((byte)0xA2);
        } else if (data.length == 16) {
            buffer.put((byte)0xA0);
        } else {
            throw new IllegalStateException();
        }
        buffer.put((byte)block);
        buffer.put(data);
    }

    int block;
    byte[] data;
}
