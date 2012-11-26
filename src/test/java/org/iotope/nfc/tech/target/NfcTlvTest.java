package org.iotope.nfc.tech.target;

import java.io.IOException;
import java.nio.charset.Charset;

import junit.framework.Assert;

import org.iotope.nfc.target.NfcTlv;
import org.iotope.util.IOUtil;
import org.junit.Test;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.common.io.Resources;

public class NfcTlvTest {
    
    
    private NfcTlv getNfcTlvAndCheck(String s) throws IOException {
        String dataAsText = Resources.toString(getClass().getResource("TLV_" + s + "_input.txt"), Charset.forName("utf8"));
        String cmpAsText = Resources.toString(getClass().getResource("TLV_" +s+"_cmp.txt"), Charset.forName("utf8"));
        byte[] inputData = IOUtil.bin2hex(dataAsText);
        
        // Read the Access Conditions
        ByteArrayDataInput dataStreamInput = ByteStreams.newDataInput(inputData);
        NfcTlv tlv = new NfcTlv();
        tlv.read(dataStreamInput);
        
        // Verify against compare file
               Assert.assertEquals(cmpAsText.replaceAll("(\\r\\n)", "\n"), tlv.toString());
        
//        // Write it back and verify
//        ByteArrayDataOutput dataStreamOutput = ByteStreams.newDataOutput();
//        tlv.write(dataStreamOutput);
//        Assert.assertEquals(IOUtil.hex(inputData), IOUtil.hex(dataStreamOutput.toByteArray()));
        return tlv;
    }
    
    @Test
    public void blockEmpty() throws IOException {
        getNfcTlvAndCheck("blockEmpty");
    }
    
    @Test
    public void blockText() throws IOException {
        getNfcTlvAndCheck("blockText");
    }
    
    @Test
    public void blockUrl() throws IOException {
        getNfcTlvAndCheck("blockUrl");
    }
    
    @Test
    public void blockTTag() throws IOException {
        getNfcTlvAndCheck("blockTTag");
    }
}
