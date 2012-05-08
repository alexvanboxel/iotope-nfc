package org.iotope.nfc.target;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.iotope.nfc.ndef.NdefMessage;

public class TargetContent {
    
    public enum TagType {
        LEGACY, // Legacy Touchatag tag
        GENERIC, // Generic tag
        IOTOPE // IOTOPE tag
    }
    
    public enum ContentType {
        NDEF, LEGACY_HASH, MEMORY_RW_BLOCK, MEMORY_RO_BLOCK,UNFORMATTED
    }
    
    public int size() {
        return list.size();
    }
    
    public Block get(int ix) {
        return list.get(ix);
    }
    
    public void add(ContentType type, byte[] content) {
        list.add(new ByteBlock(type, content));
    }
    
    public void add(ContentType type, NdefMessage content) {
        list.add(new NdefBlock(type, content));
    }
    
    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }
    
    public TagType getTagType() {
        return tagType;
    }

    public List<Block> getBlocks() {
        return Collections.unmodifiableList(list);
    }

    public Block getBlock(int ix) {
        return list.get(ix);
    }

    private List<Block> list = new ArrayList<Block>();
    private TagType tagType = TagType.GENERIC;
}
