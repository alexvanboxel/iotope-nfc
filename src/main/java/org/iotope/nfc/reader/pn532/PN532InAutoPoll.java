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

/**
 * <p>
 * This command is used to poll card(s) / target(s) of specified Type present in the RF field.
 * </p>
 * 
 * @author Alex Van Boxel <alex@vanboxel.be>
 * @see NXP PN532 User Manual - 7.3.13 InAutoPoll
 */
public class PN532InAutoPoll extends PN532AbstractCommand<PN532InAutoPoll, PN532InAutoPollResponse> {
    
    public PN532InAutoPoll() {
        super(PN532InAutoPollResponse.class);
        this.pollNr = 1;
        this.period = 1;
        this.tagTypes = new byte[] { (byte)0x00 , (byte)0x20 , (byte)0x40 }; // MiFare ,ISO/A, DEP
    }
    
    public void transfer(ByteBuffer buffer) {
        buffer.put((byte) 0xD4);
        buffer.put((byte) 0x60);
        // *** PollNr ***
        buffer.put((byte) pollNr);
        // *** Period ***
        buffer.put((byte) period);
        // *** Tags ***
        buffer.put(tagTypes);
    }
    
    public int getLength() {
        return 2 + 2 + tagTypes.length;
    }
    
    @Override
    public String toString() {
        return ">> InAutoPoll pollnr:" + pollNr + " period:"+period + " "+ hex(tagTypes);
    }

    private int pollNr;
    private int period;
    private byte[] tagTypes;
}
