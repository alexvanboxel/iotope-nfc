package org.iotope.nfc.reader;

import java.nio.ByteBuffer;

import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;

@SuppressWarnings("restriction")
public class ReaderChannel {
    
    private ReaderConnection connection;
    private CardChannel channel;
    private ByteBuffer response = ByteBuffer.allocate(512);
    private int reslen;
    
    public ReaderChannel(ReaderConnection connection, CardChannel channel) {
        this.connection = connection;
        this.channel = channel;
//        transmit(builder.getFirmwareVersion());
        this.connection.setMetaData(new String(response.array(), 0, reslen));
    }
    
    public void transmit(AbstractCommand command) {
        try {
            reslen = channel.transmit(ByteBuffer.wrap(new byte[] { (byte) 0xff, (byte) 0x00, (byte) 0x48, (byte) 0x00, (byte) 0x00 }), response);
        } catch (CardException e) {
            e.printStackTrace();
        }
    }
    
    
}
