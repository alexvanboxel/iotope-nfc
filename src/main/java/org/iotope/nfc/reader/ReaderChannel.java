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

package org.iotope.nfc.reader;

import java.nio.ByteBuffer;

import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;

@SuppressWarnings("restriction")
public class ReaderChannel {
    
    private ReaderConnection connection;
    private CardChannel channel;
    private ByteBuffer response = ByteBuffer.allocate(512);
    private int reslen;
    
    public ReaderChannel(ReaderConnection connection, CardChannel channel) {
        this.connection = connection;
        this.channel = channel;
//        transmit(builder.getFirmwareVersion());
        this.connection.setMetaData(new String(response.array(), 0, reslen));
    }
    
    public void transmit(AbstractCommand command) {
        try {
            reslen = channel.transmit(ByteBuffer.wrap(new byte[] { (byte) 0xff, (byte) 0x00, (byte) 0x48, (byte) 0x00, (byte) 0x00 }), response);
        } catch (CardException e) {
            e.printStackTrace();
        }
    }
    
    
}
