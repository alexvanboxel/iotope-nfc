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
 * Get the verion and capabilities of the embedded firmware.
 * </p>
 * 
 * @author Alex Van Boxel <alex@vanboxel.be>
 * @see NXP PN532 User Manual - 7.2.2 GetFirmwareVersion
 */
public class PN532GetFirmwareVersionResponse extends PN532AbstractResponse<PN532GetFirmwareVersion> {
    
    public PN532GetFirmwareVersionResponse(PN532GetFirmwareVersion command, ByteBuffer buffer) {
        super(command, buffer);
        checkInstruction(0x03, buffer.get());
        icVersion = buffer.get();
        firmwareVersion = buffer.get();
        firmwareRevision = buffer.get();
        capabilities = buffer.get();
    }
    
    public int getICVersion() {
        return icVersion;
    }
    
    public int getFirmwareVersion() {
        return firmwareVersion;
    }
    
    public int getFirmwareRevision() {
        return firmwareRevision;
    }
    
    public boolean isISOIEC14443A() {
        return 0x01 == (0x01 & capabilities);
    }
    
    public boolean isISOIEC14443B() {
        return 0x01 == (0x02 & capabilities);
    }
    
    public boolean isISO18092() {
        return 0x01 == (0x04 & capabilities);
    }
    
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<< GetFirmwareVersion " + " ic:" + IOUtil.hex(icVersion) + " version:" + IOUtil.hex(firmwareVersion) + " revision:" + IOUtil.hex(firmwareRevision)+ "\n");
        buffer.append("<- GetFirmwareVersion ");
        buffer.append("ISOIEC14443A: "+isISOIEC14443A()+" ");
        buffer.append("ISOIEC14443B: "+isISOIEC14443B()+" ");
        buffer.append("ISO18092: "+isISO18092()+"\n");
        return buffer.toString();
    }
    
    private int icVersion;
    private int firmwareVersion;
    private int firmwareRevision;
    private int capabilities;
}
