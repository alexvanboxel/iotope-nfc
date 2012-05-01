package org.iotope.nfc.reader.pn532.struct;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.iotope.nfc.tag.DataExchangeProtocol;
import org.iotope.nfc.tag.NfcTarget;
import org.iotope.nfc.tag.TagTechType;


public class DATA_EXCHANGE_PROTOCOL extends PN532TargetData {
    
    /**
     * [01 SENS_RES:00 02 SEL_RES:40 
    NFCID1t:len:04 08 f7 5b 64 
    NFCID3t:d7 5f 39 2f 92 b7 e1 e5 21 7b 
    DIDt:00 
    BSt:00 
    BRt:00 
    TO:0e 
    PPt:32 
    46 66 6d 01 01 10 03 02 00 01 04 01 96]}


    agFactory type: 40 targetData: [01 00 02 40 04 08 23 69 eb bc 2a 2a 4b d3 23 12 7f db b2 00 00 00 0e 32 46 66 6d 01 01 10 03 02 00 01 04 01 96]

     * @param targetData
     */
    public DATA_EXCHANGE_PROTOCOL(ByteBuffer buffer, int len) {
        super(buffer,len);
        sensRes = new byte[2];
        buffer.get(sensRes);
        selRes = buffer.get();
        byte nfcIdLength = buffer.get();
        nfcId = new byte[nfcIdLength];
        buffer.get(nfcId);
        nfc3Id = new byte[10];
        buffer.get(nfc3Id);
        // TODO : And the rest?!
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
    
    public byte[] getNfc3Id() {
        return nfc3Id;
    }
    
    public TagTechType getType() {
        return TagTechType.DATA_EXCHANGE_PROTOCOL;
    }
    
    @Override
    public NfcTarget createNfcTarget() {
        return new DataExchangeProtocol(this);
    }
    
    private byte[] sensRes;
    private byte selRes;
    private byte[] nfcId;
    private byte[] nfc3Id;
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(nfc3Id);
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
        return true;
    }
}
