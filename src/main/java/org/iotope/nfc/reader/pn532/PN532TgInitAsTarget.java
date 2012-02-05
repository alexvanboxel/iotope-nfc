/*
 * Copyright (C) 2012 IOTOPE (http://www.iotope.com/)
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
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

/**
 * <p>
 * Configure the PN532 as target.
 * </p>
 * 
 * @author Alex Van Boxel <alex@vanboxel.be>
 * @see NXP PN532 User Manual - 7.3.14 TgInitAsTarget
 */
public class PN532TgInitAsTarget extends PN532AbstractCommand<PN532TgInitAsTarget, PN532TgInitAsTargetResponse> {
    
    public PN532TgInitAsTarget() {
        super(PN532TgInitAsTargetResponse.class);
    }
    
    
    public void transfer(ByteBuffer buffer) {
        buffer.put((byte) 0xD4);
        buffer.put((byte) 0x8C);
        // *** Mode ***
        buffer.put((byte) 0x00);
        // *** MifareParams (6 bytes) ***
        buffer.put(new byte[] { (byte) 0x08, (byte) 0x00, (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x40 });
        // *** FeliCaParams (18 bytes) ***
        buffer.put(new byte[] { (byte) 0x01, (byte) 0xFE, (byte) 0xA2, (byte) 0xA3, (byte) 0xA4, (byte) 0xA5, //
                (byte) 0xA6, (byte) 0xA7, (byte) 0xC0, (byte) 0xC1, (byte) 0xC2, (byte) 0xC3, //
                (byte) 0xC4, (byte) 0xC5, (byte) 0xC6, (byte) 0xC7, (byte) 0xFF, (byte) 0xFF });
        // *** NFCID3 (10 bytes) IOTOPE0000 ***
        buffer.put(new byte[] { (byte) 0x49, (byte) 0x4F, (byte) 0x54, (byte) 0x4F, (byte) 0x50, (byte) 0x45, //
                (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30 });
        // *** G ***
        buffer.put((byte) 0x00);
        // *** LEN ***
        buffer.put((byte) 0x00);
    }
    

    public int getInstruction() { return 0x8C; }

    public int getLength() {
        return 2 + 1 + 6 + 18 + 10 + 2;
    }
}
