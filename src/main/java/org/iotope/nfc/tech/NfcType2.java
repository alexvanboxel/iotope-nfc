package org.iotope.nfc.tech;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.iotope.nfc.ndef.NdefMessage;
import org.iotope.nfc.ndef.NdefReader;
import org.iotope.nfc.reader.ReaderChannel;
import org.iotope.nfc.reader.pn532.PN532InDataExchange;
import org.iotope.nfc.reader.pn532.PN532InDataExchangeResponse;
import org.iotope.nfc.tag.NfcTag;
import org.iotope.nfc.tech.mifare.MifareAuthenticate;
import org.iotope.nfc.tech.mifare.MifareReadBlock;
import org.iotope.nfc.tech.mifare.MifareWriteBlock;
import org.iotope.util.IOUtil;

public class NfcType2 {
    
    
    public NfcType2(ReaderChannel channel) {
        super();
        this.channel = channel;
    }
    
    public byte[] read(NfcTag nfcTag) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (int page = 0; page < 4; page++) {
            int blockAddress = page * 4;
            MifareReadBlock readCommand = new MifareReadBlock(blockAddress);
            PN532InDataExchangeResponse response = channel.transmit(new PN532InDataExchange(1, readCommand));
            byte[] responseBytes = response.getData();
            output.write(responseBytes, 0, 16);
            System.out.println(IOUtil.hex(responseBytes));
        }
        return output.toByteArray();
    }
    
    public void readNDEF(NfcTag nfcTag) throws Exception {
        byte[] content = read(nfcTag);
        ByteBuffer buffer = ByteBuffer.wrap(content);
        // skip the first 16 bytes
        buffer.position(16);
        // read the TLV
        boolean readTLV = true;
        int tlvC = 0;
        while (readTLV) {
            if (++tlvC > 1) {
                // only read the first TLV block 
                readTLV = false;
                continue;
            }
            byte tlvT = buffer.get();
            int tlvL;
            switch (tlvT) {
            case (byte) 0x00: // 2.3.1 NULL TLV
                break;
            case (byte) 0x03: // 2.3.4 NDEF Message TLV
                tlvL = buffer.get();
                System.out.println("TLV NDEF L" + tlvL);
                byte[] ndefBuffer = new byte[tlvL];
                buffer.get(ndefBuffer);
                NdefReader ndefReader = new NdefReader(ndefBuffer);
                NdefMessage ndefMessage = ndefReader.parse();
                
                System.out.println(new String(ndefBuffer));
                break;
            case (byte) 0xFD: // 2.3.5 Proprietary TLV
                tlvL = buffer.get();
                System.out.println("TLV prop L" + tlvL);
                byte[] propBuffer = new byte[tlvL];
                buffer.get(propBuffer);
                break;
            case (byte) 0xFE: // 2.3.6 Terminator TLV
                readTLV = false;
                break;
            default:
                System.out.println("Unknown TLV: " + IOUtil.hex(tlvT));
            }
        }
        
    }
    
    public void writeTest(NfcTag nfcTag) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        MifareWriteBlock writeCommand = new MifareWriteBlock(3, new byte[] { (byte) 0xE1, (byte) 0x10, (byte) 0x06, (byte) 0x00 });
        PN532InDataExchangeResponse response = channel.transmit(new PN532InDataExchange(1, writeCommand));
        
        writeCommand = new MifareWriteBlock(4, new byte[] { (byte) 0x03, (byte) 0x00, (byte) 0xFE, (byte) 0x00 });
        response = channel.transmit(new PN532InDataExchange(1, writeCommand));
        
        //        for (int page16 = 3; page16 < 4; page16++) {
        //            int blockA = page16 * 4;
        //            MifareReadBlock readCommand = new MifareReadBlock(blockA);
        //            PN532InDataExchangeResponse response = channel.transmit(new PN532InDataExchange(1, readCommand));
        //            byte[] responseBytes = response.getData();
        //            System.out.println(IOUtil.hex(responseBytes));
        //        }
    }
    
    
    public byte[] readClassicAll(NfcTag nfcTag) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        // If we attempt to read from sector 0, the sectors we can actually read timeout as well !
        for (int sector = 0; sector < 16; sector++) {
            if (!authenticateSector(nfcTag, sector)) {
                //                    log.debug("Failed to authenticate " + getTag() + " sector " + sector);
            }
            // Even on authentication failure we attempt to read
            byte[] sectorData = readSector(sector);
            output.write(sectorData, 0, sectorData.length);
        }
        return output.toByteArray();
    }
    
    
    private boolean authenticateSector(NfcTag nfcTag, int sector) throws Exception {
        int blockAddr = sector * 4;
        
        List<byte[]> keys = new ArrayList<byte[]>();
        keys.add(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF });
        keys.add(new byte[] { (byte) 0xD3, (byte) 0xF7, (byte) 0xD3, (byte) 0xF7, (byte) 0xD3, (byte) 0xF7 });
        
        for (byte[] key : keys) {
            byte[] id = nfcTag.getNfcId();
            if (authenticate(MifareAuthenticate.AuthKey.A, blockAddr, id, key)) {
                return true;
            }
            //            reselect();
            if (authenticate(MifareAuthenticate.AuthKey.B, blockAddr, id, key)) {
                return true;
            }
            //            reselect();
        }
        return false;
    }
    
    private boolean authenticate(MifareAuthenticate.AuthKey auth, int block, byte[] id, byte[] key) throws Exception {
        MifareAuthenticate authenticate = new MifareAuthenticate(auth, block, key, id);
        PN532InDataExchangeResponse response = channel.transmit(new PN532InDataExchange(1, authenticate));
        
        byte[] responseBytes = response.getData();
        if (responseBytes[2] == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    private byte[] readSector(int sector) throws Exception {
        ByteArrayOutputStream sectorData = new ByteArrayOutputStream();
        for (int block = 0; block < 4; block++) {
            
            byte[] tag = new byte[] { (byte) 0x30, (byte) 0x00 };
            
            int i = (sector * 4) + block;
            //int i = sector;
            tag[1] = (byte) i;
            
            PN532InDataExchangeResponse response = channel.transmit(new PN532InDataExchange(1, tag));
            byte[] responseBytes = response.getData();
            System.out.println(IOUtil.hex(responseBytes));
            //Command cmd = com.tikitag.client.tagservice.tag.mifare.MiFareCommandFactory.readTagForBlock((sector * 4) + block);
            // = getReader().sendTagCommand(cmd, getTag());
            //            log.debug("Read sector=" + sector + " block=" + block + " : " + HexFormatter.toHexString(responseBytes));
            
            //            if (responseBytes[2] == 0x00) {
            //                // Extract the relevant part
            //                sectorData.write(responseBytes, 3, responseBytes.length - 3);
            //            } else {
            //                sectorData.write(UNREAD_BLOCK, 0, UNREAD_BLOCK.length);
            //            }
            //        }
        }
        return sectorData.toByteArray();
        
    }
    
    private ReaderChannel channel;
}
