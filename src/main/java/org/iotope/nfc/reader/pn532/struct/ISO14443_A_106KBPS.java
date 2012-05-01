package org.iotope.nfc.reader.pn532.struct;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.iotope.nfc.tag.MifareClassic;
import org.iotope.nfc.tag.MifareUltraLight;
import org.iotope.nfc.tag.NfcTarget;
import org.iotope.nfc.tag.TagTechType;


public class ISO14443_A_106KBPS extends PN532TargetData {
        
    public ISO14443_A_106KBPS(ByteBuffer buffer, int len) {
        super(buffer,len);
        sensRes = new byte[2];
        buffer.get(sensRes);
        selRes = buffer.get();
        byte nfcIdLength = buffer.get();
        nfcId = new byte[nfcIdLength];
        buffer.get(nfcId);
        remaining(buffer);
    }
        
    public byte[] getSensRes() {
        return sensRes;
    }
    
    public byte getSelRes() {
        return selRes;
    }
    
    public byte[] getNfc1Id() {
        return nfcId;
    }
    
    public TagTechType getType() {
        switch (selRes) {
        case (byte) 0x00:
            return TagTechType.MIFARE_ULTRALIGHT;
        case (byte) 0x08:
            return TagTechType.MIFARE_1K;
        case (byte) 0x09:
            return TagTechType.MIFARE_MINI;
        case (byte) 0x18:
            return TagTechType.MIFARE_4K;
        case (byte) 0x20:
            return TagTechType.MIFARE_DESFIRE;
        case (byte) 0x28:
            return TagTechType.JCOP30;
        case (byte) 0x98:
            return TagTechType.GEMPLUS_MPCOS;
        }
        System.err.println("getType() for selRes: "+selRes);
        return null;
    }
    
    public NfcTarget createNfcTarget() {
        TagTechType type = getType();
        if(type == null) {
            System.err.println("Can't create NfcTarget. Don't know type...");
        }
        
        
        switch (type) {
        case MIFARE_1K:
            return new MifareClassic(this);
        case MIFARE_ULTRALIGHT:
            return new MifareUltraLight(this);
        }
        return null;
    }

    private byte[] sensRes;
    private byte selRes;
    private byte[] nfcId;
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(nfcId);
        result = prime * result + selRes;
        result = prime * result + Arrays.hashCode(sensRes);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ISO14443_A_106KBPS other = (ISO14443_A_106KBPS) obj;
        if (!Arrays.equals(nfcId, other.nfcId))
            return false;
        if (selRes != other.selRes)
            return false;
        if (!Arrays.equals(sensRes, other.sensRes))
            return false;
        return true;
    }
}
