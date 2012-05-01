package org.iotope.nfc.tag;

import java.util.ArrayList;
import java.util.List;

import org.iotope.nfc.ndef.NdefMessage;

public class TagContent {
    
    public enum TagType {
        LEGACY, // Legacy Touchatag tag
        GENERIC, // Generic tag
        IOTOPE // IOTOPE tag
    }
    
    public enum ContentType {
        NDEF, LEGACY_HASH, MEMORY_RW_BLOCK, MEMORY_RO_BLOCK,UNFORMATTED
    }
    
    public class Content {
        protected ContentType type;
        
        public ContentType getType() {
            return type;
        }
    }
    
    public class ByteContent extends Content {
        private byte[] content;
        
        private ByteContent(ContentType type, byte[] content) {
            this.type = type;
            this.content = content;
        }
        
        public byte[] getContent() {
            return content;
        }
    }
    
    public class NdefContent extends Content {
        private NdefContent(ContentType type, NdefMessage content) {
            this.type = type;
            this.ndef = content;
        }
        
        public NdefMessage getNdef() {
            return ndef;
        }
        
        private NdefMessage ndef;
    }
    
    public int size() {
        return list.size();
    }
    
    public Content get(int ix) {
        return list.get(ix);
    }
    
    public void add(ContentType type, byte[] content) {
        list.add(new ByteContent(type, content));
    }
    
    public void add(ContentType type, NdefMessage content) {
        list.add(new NdefContent(type, content));
    }
    
    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }
    
    public TagType getTagType() {
        return tagType;
    }
    
    private List<Content> list = new ArrayList<Content>();
    private TagType tagType = TagType.GENERIC;
}
