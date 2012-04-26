package org.iotope.nfc.reader.pn532.struct;

import org.iotope.nfc.tag.TagType;


public class ISO14443_A_106KBPS extends PN532TargetData {
    
    public ISO14443_A_106KBPS(byte[] targetData) {
        //        Validate.notNull(targetData, "TargetData cannot be null");
        //        Validate.isTrue(targetData.length >= 5);
        byte target = targetData[0];
        byte[] sensRes = new byte[2];
        System.arraycopy(targetData, 1, sensRes, 0, 2);
        byte selRes = targetData[3];
        byte nfcIdLength = targetData[4];
        //        Validate.isTrue(nfcIdLength > 0, "NFCIdLength must be greater than 0");
        //        Validate.isTrue(nfcIdLength <= targetData.length - 5, "NFCIDLength cannot be longer than the remaining data length of TargetData");
        byte[] nfcId = new byte[nfcIdLength];
        System.arraycopy(targetData, 5, nfcId, 0, nfcIdLength);
        this.target = target;
        this.sensRes = sensRes;
        this.selRes = selRes;
        this.nfcIdLength = nfcIdLength;
        this.nfcId = nfcId;
    }
    
    public byte getTarget() {
        return target;
    }
    
    public byte[] getSensRes() {
        return sensRes;
    }
    
    public byte getSelRes() {
        return selRes;
    }
    
    public byte getNfcIdLength() {
        return nfcIdLength;
    }
    
    public byte[] getNfcId() {
        return nfcId;
    }
    
    public TagType getType() {
        switch (selRes) {
        case (byte) 0x00:
            return TagType.MIFARE_ULTRALIGHT;
        case (byte) 0x08:
            return TagType.MIFARE_1K;
        case (byte) 0x09:
            return TagType.MIFARE_MINI;
        case (byte) 0x18:
            return TagType.MIFARE_4K;
        case (byte) 0x20:
            return TagType.MIFARE_DESFIRE;
        case (byte) 0x28:
            return TagType.JCOP30;
        case (byte) 0x98:
            return TagType.GEMPLUS_MPCOS;
        }
        return null;
    }
    
    private byte target;
    private byte[] sensRes;
    private byte selRes;
    private byte nfcIdLength;
    private byte[] nfcId;
}
