package org.iotope.nfc.tech.mifare;

import java.nio.ByteBuffer;

import org.iotope.nfc.reader.ReaderTransmit;

public class MifareAuthenticate implements ReaderTransmit {

    public MifareAuthenticate(AuthKey selectedKey, int block, byte[] key, byte[] id) {
        super();
        this.selectedKey = selectedKey;
        this.block = block;
        this.key = key;
        this.id = id;
    }
    
    @Override
    public int getLength() {
        return 2+key.length+id.length;
    }
    
    @Override
    public void transfer(ByteBuffer buffer) {
        switch (selectedKey) {
        case A:
            buffer.put((byte) 0x60);
            break;
        case B:
            buffer.put((byte) 0x61);
            break;
        default:
            throw new IllegalStateException();
        }
        buffer.put((byte) block);
        buffer.put(key);
        buffer.put(id);
    }
    
    public static enum AuthKey {
        A, B
    }
    
    AuthKey selectedKey;
    int block;
    byte[] key;
    byte[] id;
}
