package org.iotope.nfc.target;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.iotope.nfc.ndef.NdefParsedMessage;
import org.iotope.nfc.ndef.NdefParsedRecord;
import org.iotope.nfc.ndef.NdefRTDURI;
import org.iotope.nfc.ndef.NdefReader;
import org.iotope.nfc.tech.DataIO;
import org.iotope.nfc.tech.NfcType2;
import org.iotope.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NfcTlv implements DataIO {
    private static Logger Log = LoggerFactory.getLogger(NfcTlv.class);
    
    public enum TagType {
        LEGACY, // Legacy Touchatag tag
        GENERIC, // Generic tag
        IOTOPE // IOTOPE tag
    }
    
    public enum ContentType {
        NDEF, LEGACY_HASH, MEMORY_RW_BLOCK, MEMORY_RO_BLOCK,UNFORMATTED, LEGACY_TAGDATA
    }
    
    public int size() {
        return list.size();
    }
    
    public TlvBlock get(int ix) {
        return list.get(ix);
    }
    
    public void add(ContentType type, byte[] content) {
        list.add(new TlvByteBlock(type, content));
    }
    
    public void add(ContentType type, NdefParsedMessage content) {
        list.add(new TlvNdefBlock(type, content));
    }
    
    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }
    
    public TagType getTagType() {
        return tagType;
    }

    public List<TlvBlock> getBlocks() {
        return Collections.unmodifiableList(list);
    }

    public TlvBlock getBlock(int ix) {
        return list.get(ix);
    }

    private List<TlvBlock> list = new ArrayList<TlvBlock>();
    private TagType tagType = TagType.GENERIC;

    @Override
    public void read(DataInput buffer) throws IOException {
        try {
//            byte[] content = read(nfcTag);
//            ByteBuffer buffer = ByteBuffer.wrap(content);
            // skip the first 16 bytes
            buffer.skipBytes(16);
            // read the TLV
            boolean readTLV = true;
            int tlvC = 0;
            while (readTLV) {
                byte tlvT = buffer.readByte();
                int tlvLength;
                switch (tlvT) {
                case (byte) 0x00: // 2.3.1 NULL TLV
                    Log.debug("TLV NULL");
                    break;
                case (byte) 0x03: // 2.3.4 NDEF Message TLV
                    tlvLength = buffer.readByte();
                    Log.debug("TLV NDEF L" + tlvLength);
                    byte[] ndefBuffer = new byte[tlvLength];
                    buffer.readFully(ndefBuffer);
                    NdefReader ndefReader = new NdefReader(ndefBuffer);
                    NdefParsedMessage ndefMessage = ndefReader.parse();
                    // Special handling for Touchatag tag:
                    // because it doesn't comply to the Type 2 layout
                    // It only has a TLV block for the NDEF message
                    if ((tlvC == 0) && (ndefMessage.size() == 1)) {
                        NdefParsedRecord record = ndefMessage.getRecord(0);
                        if (record instanceof NdefRTDURI) {
                            URI uri = ((NdefRTDURI) record).getURI();
                            if ("www.ttag.be".equals(uri.getHost())) {
                                setTagType(TagType.LEGACY);
                                add(ContentType.NDEF, ndefMessage);
                                byte[] buf = new byte[13];
                                buffer.readFully(buf);
                                add(ContentType.LEGACY_HASH, buf);
                                buf = new byte[4];
                                buffer.readFully(buf);
                                add(ContentType.MEMORY_RW_BLOCK, buf);
                                return;
                            }
                        }
                    }
                    add(ContentType.NDEF, ndefMessage);
                    break;
                case (byte) 0xFD: // 2.3.5 Proprietary TLV
                    tlvLength = buffer.readByte();
                    Log.debug("TLV Proprietary L" + tlvLength);
                    byte[] propBuffer = new byte[tlvLength];
                    buffer.readFully(propBuffer);
                    break;
                case (byte) 0xFE: // 2.3.6 Terminator TLV
                    Log.debug("TLV Terminator");
                    readTLV = false;
                    break;
                default:
                    Log.error("Unknown TLV: " + IOUtil.hex(tlvT));
//                    byte[] buf = new byte[content.length - buffer.position() + 1];
//                    buf[0] = tlvT;
//                    buffer.get(buf, 1, content.length - buffer.position());
//                    tagContent.add(ContentType.UNFORMATTED, buf);
                    return;
                }
                tlvC++;
            }
        }
        catch(Throwable e) {
            Log.error("Can't read NDEF, maybe tag was removed "+e.getMessage());
        }
        return;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        for (TlvBlock block : list) {
            if(block instanceof TlvNdefBlock) {
                ((TlvNdefBlock) block).getNdef().write(output);
            }
            
        }
        // TODO Auto-generated method stub
        
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for(TlvBlock block : list) {
            buffer.append('[');
            buffer.append(block.toString());
            buffer.append(']');
        }
        return buffer.toString();
    }
    
    
}
