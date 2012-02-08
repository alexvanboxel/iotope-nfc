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
 * This command is used to support protocol data exchanges between the PN532 as
 * initiator and a target.
 * </p>
 * 
 * @author Alex Van Boxel <alex@vanboxel.be>
 * @see NXP PN532 User Manual - 7.3.8 InDataExchange
 */
public class PN532InDataExchange extends PN532AbstractCommand<PN532InDataExchange, PN532InDataExchangeResponse> {
    
    /**
     *  
     * @param target logical target
     * @param mi More Information
     * @param dataOut data
     */
    public PN532InDataExchange(int target, boolean mi, byte[] dataOut) {
        super(PN532InDataExchangeResponse.class);
        this.dataOut = dataOut;
        this.target = target;
        this.mi = mi;
    }
    
    public void transfer(ByteBuffer buffer) {
        buffer.put((byte) 0xD4);
        buffer.put((byte) 0x40);
        // *** Tg ***
        int tg = target;
        if (mi)
            tg = IOUtil.bitset(tg, 6);
        buffer.put((byte) tg);
        // *** DataOut ***
        buffer.put(dataOut);
    }
    
    public int getLength() {
        return 2 + 1 + dataOut.length;
    }
    
    private int target;
    private boolean mi;
    private byte[] dataOut;
}
