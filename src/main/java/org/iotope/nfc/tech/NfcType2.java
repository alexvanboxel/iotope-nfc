package org.iotope.nfc.tech;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.iotope.nfc.ndef.NdefMessage;
import org.iotope.nfc.ndef.NdefRTDURI;
import org.iotope.nfc.ndef.NdefReader;
import org.iotope.nfc.ndef.NdefRecord;
import org.iotope.nfc.reader.ReaderChannel;
import org.iotope.nfc.reader.pn532.PN532InDataExchange;
import org.iotope.nfc.reader.pn532.PN532InDataExchangeResponse;
import org.iotope.nfc.tag.NfcTarget;
import org.iotope.nfc.target.TargetContent;
import org.iotope.nfc.target.TargetContent.ContentType;
import org.iotope.nfc.target.TargetContent.TagType;
import org.iotope.nfc.tech.mifare.MifareAuthenticate;
import org.iotope.nfc.tech.mifare.MifareReadBlock;
import org.iotope.nfc.tech.mifare.MifareWriteBlock;
import org.iotope.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NfcType2 {
    private static Logger Log = LoggerFactory.getLogger(NfcType2.class);
    
    public NfcType2(ReaderChannel channel) {
        super();
        this.channel = channel;
    }
    
    public byte[] read(NfcTarget nfcTag) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (int page = 0; page < 4; page++) {
            int blockAddress = page * 4;
            MifareReadBlock readCommand = new MifareReadBlock(blockAddress);
            PN532InDataExchangeResponse response = channel.transmit(new PN532InDataExchange(1, readCommand));
            byte[] responseBytes = response.getData();
            output.write(responseBytes, 0, 16);
            Log.debug("Page " + page + " content : " + IOUtil.hex(responseBytes));
        }
        return output.toByteArray();
    }
    
    public TargetContent readNDEF(NfcTarget nfcTag) throws Exception {
        try {
            TargetContent tagContent = new TargetContent();
            byte[] content = read(nfcTag);
            ByteBuffer buffer = ByteBuffer.wrap(content);
            // skip the first 16 bytes
            buffer.position(16);
            // read the TLV
            boolean readTLV = true;
            int tlvC = 0;
            while (readTLV) {
                byte tlvT = buffer.get();
                int tlvL;
                switch (tlvT) {
                case (byte) 0x00: // 2.3.1 NULL TLV
                    Log.debug("TLV NULL");
                    break;
                case (byte) 0x03: // 2.3.4 NDEF Message TLV
                    tlvL = buffer.get();
                    Log.debug("TLV NDEF L" + tlvL);
                    byte[] ndefBuffer = new byte[tlvL];
                    buffer.get(ndefBuffer);
                    NdefReader ndefReader = new NdefReader(ndefBuffer);
                    NdefMessage ndefMessage = ndefReader.parse();
                    // Special handling for Touchatag tag:
                    // because it doesn't comply to the Type 2 layout
                    // It only has a TLV block for the NDEF message
                    if ((tlvC == 0) && (ndefMessage.size() == 1)) {
                        NdefRecord record = ndefMessage.getRecord(0);
                        if (record instanceof NdefRTDURI) {
                            URI uri = ((NdefRTDURI) record).getURI();
                            if ("www.ttag.be".equals(uri.getHost())) {
                                tagContent.setTagType(TagType.LEGACY);
                                tagContent.add(ContentType.NDEF, ndefMessage);
                                byte[] buf = new byte[13];
                                buffer.get(buf);
                                tagContent.add(ContentType.LEGACY_HASH, buf);
                                buf = new byte[4];
                                buffer.get(buf);
                                tagContent.add(ContentType.MEMORY_RW_BLOCK, buf);
                                tagContent.add(ContentType.LEGACY_TAGDATA, content);
                                return tagContent;
                            }
                            else {
                                tagContent.add(ContentType.NDEF, ndefMessage);
                            }
                        }
                        // 
                    }
                    break;
                case (byte) 0xFD: // 2.3.5 Proprietary TLV
                    tlvL = buffer.get();
                    Log.debug("TLV Proprietary L" + tlvL);
                    byte[] propBuffer = new byte[tlvL];
                    buffer.get(propBuffer);
                    break;
                case (byte) 0xFE: // 2.3.6 Terminator TLV
                    Log.debug("TLV Terminator");
                    readTLV = false;
                    break;
                default:
                    Log.error("Unknown TLV: " + IOUtil.hex(tlvT));
                    byte[] buf = new byte[content.length - buffer.position() + 1];
                    buf[0] = tlvT;
                    buffer.get(buf, 1, content.length - buffer.position());
                    tagContent.add(ContentType.UNFORMATTED, buf);
                    return tagContent;
                }
                tlvC++;
            }
            return tagContent;
        }
        catch(Throwable e) {
            Log.error("Can't read NDEF, maybe tag was removed "+e.getMessage());
            return null;
        }
    }
    
    public void writeTest(NfcTarget nfcTag) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        MifareWriteBlock writeCommand = new MifareWriteBlock(3, new byte[] { (byte) 0xE1, (byte) 0x10, (byte) 0x06, (byte) 0x00 });
        PN532InDataExchangeResponse response = channel.transmit(new PN532InDataExchange(1, writeCommand));
        
        writeCommand = new MifareWriteBlock(4, new byte[] { (byte) 0x03, (byte) 0x00, (byte) 0xFE, (byte) 0x00 });
        response = channel.transmit(new PN532InDataExchange(1, writeCommand));
    }
    
    
    public byte[] readClassicAll(NfcTarget nfcTag) throws Exception {
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
    
    
    private boolean authenticateSector(NfcTarget nfcTag, int sector) throws Exception {
        int blockAddr = sector * 4;
        
        List<byte[]> keys = new ArrayList<byte[]>();
        keys.add(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF });
        keys.add(new byte[] { (byte) 0xD3, (byte) 0xF7, (byte) 0xD3, (byte) 0xF7, (byte) 0xD3, (byte) 0xF7 });
        
        for (byte[] key : keys) {
            byte[] id = nfcTag.getNfc1Id();
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
            tag[1] = (byte) i;
            
            PN532InDataExchangeResponse response = channel.transmit(new PN532InDataExchange(1, tag));
            byte[] responseBytes = response.getData();
        }
        return sectorData.toByteArray();
        
    }
    
    private ReaderChannel channel;
    
}
