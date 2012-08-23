package org.iotope.nfc.target;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.iotope.nfc.ndef.NdefParsedMessage;
import org.iotope.nfc.tech.DataIO;

public class NfcTlv implements DataIO {
    
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
    public void read(DataInput input) throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void write(DataOutput output) throws IOException {
        // TODO Auto-generated method stub
        
    }
}
