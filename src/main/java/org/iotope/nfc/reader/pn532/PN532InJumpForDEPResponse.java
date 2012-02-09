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

import java.nio.ByteBuffer;

import org.iotope.nfc.reader.pn532.struct.PN532Status;
import org.iotope.util.IOUtil;

public class PN532InJumpForDEPResponse extends PN532AbstractResponse<PN532InJumpForDEP> {
    
    public PN532InJumpForDEPResponse(PN532InJumpForDEP command, ByteBuffer buffer) {
        super(command, buffer);
        checkInstruction(0x57, buffer.get());
        status = new PN532Status(buffer);
        tg = buffer.get();
        buffer.get(nfcid3);
        did = buffer.get();
        bs = buffer.get();
        br = buffer.get();
        to = buffer.get();
        pp = buffer.get();
        g = new byte[buffer.remaining()];
        buffer.get(g);
        // D5 57 Status Tg NFCID3t[0..9] DIDt BSt BRt
        // TO PPt [Gt [0..n]]
    }
        
    @Override
    public String toString() {
        return "< InJumpForDEP " + status + " " + tg + " nfcid3:" + IOUtil.hex(nfcid3) + " did:" + IOUtil.hex(did) + " bs:" + IOUtil.hex(bs) //
                + " br:" + IOUtil.hex(br) + " to:" + IOUtil.hex(to) + " pp:" + IOUtil.hex(pp) + " g:" + IOUtil.hex(g);
    }
    
    private PN532Status status;
    private int tg;
    private byte[] nfcid3 = new byte[9];
    private int did, bs, br, to, pp;
    private byte[] g;
}
