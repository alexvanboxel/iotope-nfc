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

import org.iotope.util.IOUtil;

/**
 * <p>
 * This command is used in case of the PN532 configured as target for Data
 * Exchange Protocol (DEP) or for ISO/IEC14443-4 protocol when PN532 is
 * activated in ISO/IEC14443-4 PICC emulated (see §4, p:21). The overall amount
 * of data to be sent can be transmitted in one frame (262 bytes maximum).
 * </p>
 * 
 * @author Alex Van Boxel <alex@vanboxel.be>
 * @see NXP PN532 User Manual - 7.3.17 TgSetData
 */
public class PN532TgSetData extends PN532AbstractCommand<PN532TgSetData, PN532TgSetDataResponse> {
    
    public PN532TgSetData(byte[] dataOut) {
        super(PN532TgSetDataResponse.class);
        this.dataOut = dataOut;
    }
    
    public PN532TgSetData() {
        super(PN532TgSetDataResponse.class);
        this.dataOut = new byte[0];
    }
    
    public void transfer(ByteBuffer buffer) {
        buffer.put((byte) 0xD4);
        buffer.put((byte) 0x8E);
        // *** DataOut ***
        buffer.put(dataOut);
    }
    
    public int getLength() {
        return 2 + dataOut.length;
    }
    
    @Override
    public String toString() {
        return ">> TgSetData "+IOUtil.hex(dataOut);
    }

    private byte[] dataOut;
}
