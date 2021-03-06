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

public class PN532InRelease extends PN532AbstractCommand<PN532InRelease, PN532InReleaseResponse> {
    
    public PN532InRelease() {
        super(PN532InReleaseResponse.class);
    }
    
    public void transfer(ByteBuffer buffer) {
        buffer.put((byte) 0xD4);
        buffer.put((byte) 0x52);
        // *** Tg ***
        buffer.put((byte) 0x00); // all targets for now
    }
    
    @Override
    public int getLength() {
        return 2 + 1;
    }
    

    @Override
    public String toString() {
        return ">> InRelease Tg:"+IOUtil.hex(0);
    }
}
