/*
 * Copyright (C) 2012 IOTOPE (http://www.iotope.com/)
 *
 * Licensed to IOTOPE under one or more contributor license 
 * agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * IOTOPE licenses this file to you under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file except 
 * in compliance with the License.  You may obtain a copy of the 
 * License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.iotope.nfc.reader.pn532;

import static org.iotope.util.IOUtil.hex;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This command is used to poll card(s) / target(s) of specified Type present in the RF field.
 * </p>
 * 
 * @author Alex Van Boxel <alex@vanboxel.be>
 * @see NXP PN532 User Manual - 7.3.13 InAutoPoll
 */
public class PN532InAutoPollResponse extends PN532AbstractResponse<PN532InAutoPoll> {
    
    public PN532InAutoPollResponse(PN532InAutoPoll command, ByteBuffer buffer) {
        super(command, buffer);
        checkInstruction(0x61, buffer.get());
        int nr = buffer.get();
        tags = new TargetData[nr];
        for (int i = 0; i < nr; i++) {
            tags[i] = new TargetData(buffer);
        }
    }
    
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<< InAutoPoll len:"+hex(tags.length)+"\n");
        for (TargetData td : tags) {
            buffer.append("<- InAutoPoll ");
            buffer.append(td);
            buffer.append("\n");
        }
        return buffer.toString();
    }
    
    public List<String> getRawTagdata() {
        ArrayList<String> list = new ArrayList<String>();
        for (TargetData td : tags) {
            list.add(td.toString());
        }
        return list;
    }
    /*
ttag
<- InAutoPoll {TargetData type: 10 len:0c [01 00 44 00 lenid:07 04 47 b9 9a 34 23 80]}
<- InAutoPoll {TargetData type: 10 len:0c [02 00 44 00 lenid:07 04 75 be 9a 34 23 80]}
yubico
<- InAutoPoll {TargetData type: 10 len:09 [01 00 04 08 lenid:04 84 54 62 0b]}
android
<- InAutoPoll len:01{TargetData type: 40 len:112 
[01 SENS_RES:00 02 SEL_RES:40 
NFCID1t:len:04 08 f7 5b 64 
NFCID3t:d7 5f 39 2f 92 b7 e1 e5 21 7b 
DIDt:00 
BSt:00 
BRt:00 
TO:0e 
PPt:32 
46 66 6d 01 01 10 03 02 00 01 04 01 96]}


HOME
<- InAutoPoll {TargetData type: 40 len:25 [01 00 02 40 04 08 d2 ed 1c 70 91 0d 25 0a a0 ea 9e c3 39 00 00 00 0e 32 46 66 6d 01 01 10 03 02 00 01 04 01 96]}
<- InAutoPoll {TargetData type: 40 len:25 [01 00 02 40 04 08 a6 ba 9d 2b d5 47 4e e3 60 34 59 5e db 00 00 00 0e 32 46 66 6d 01 01 10 03 02 00 01 04 01 96]}
<- InAutoPoll {TargetData type: 40 len:25 [01 00 02 40 04 08 47 7a 3f b5 85 2f 6a 47 c3 21 b9 18 2b 00 00 00 0e 32 46 66 6d 01 01 10 03 02 00 01 04 01 96]}
YouTube
<- InAutoPoll {TargetData type: 40 len:25 [01 00 02 40 04 08 06 47 9f 56 54 22 c9 ce 56 66 b9 6e 9a 00 00 00 0e 32 46 66 6d 01 01 10 03 02 00 01 04 01 96]}
<- InAutoPoll {TargetData type: 40 len:25 [01 00 02 40 04 08 3d 60 1f 4b 2b c5 10 ae 76 7e 01 69 ce 00 00 00 0e 32 46 66 6d 01 01 10 03 02 00 01 04 01 96]}
<- InAutoPoll {TargetData type: 40 len:25 [01 00 02 40 04 08 90 46 35 57 0d 1d c0 b9 2e b3 41 6d 2f 00 00 00 0e 32 46 66 6d 01 01 10 03 02 00 01 04 01 96]}





<- InAutoPoll len:01{TargetData type: 40 len:112 [01 00 02 40 04 08 04 02 d1 7c dc 93 fd 40 c4 56 fd 7a b8 00 00 00 0e 32 46 66 6d 01 01 10 03 02 00 01 04 01 96]}
<- InAutoPoll len:01{TargetData type: 40 len:112 [01 00 02 40 04 08 77 81 c3 b3 fd 92 ff 6e 9a 25 76 2f fd 00 00 00 0e 32 46 66 6d 01 01 10 03 02 00 01 04 01 96]}
<- InAutoPoll len:01{TargetData type: 40 len:112 [01 00 02 40 04 08 65 00 dc 80 a4 29 1c 34 84 c2 19 18 81 00 00 00 0e 32 46 66 6d 01 01 10 03 02 00 01 04 01 96]}
<- InAutoPoll len:01{TargetData type: 40 len:112 [01 00 02 40 04 08 d7 aa 84 53 c2 66 67 b9 90 c4 ce 6f e7 00 00 00 0e 32 46 66 6d 01 01 10 03 02 00 01 04 01 96]}
     */
    private class TargetData {
        
        TargetData(ByteBuffer buffer) {
            type = buffer.get();
            int len = buffer.get();
            targetData = new byte[len];
            buffer.get(targetData);
        }
        
        @Override
        public String toString() {
            return "{TargetData type: " + hex(type) + " len:" + hex(targetData.length)+" "+ hex(targetData) + "}";
        }
        
        private int type;
        private byte[] targetData;
    }
    
    private TargetData[] tags;
}
