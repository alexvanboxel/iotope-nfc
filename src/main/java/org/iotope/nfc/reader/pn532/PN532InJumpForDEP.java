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
 * This command is used by a host controller to activate a target using either active or passive communication mode. If a target is in the field, it will then be ready for DEP (ISO/IEC18092 Data Exchange Protocol) exchanges.
 * </p>
 * 
 * @author Alex Van Boxel <alex@vanboxel.be>
 * @see NXP PN532 User Manual - 7.3.3 InJumpForDEP
 */
public class PN532InJumpForDEP extends PN532AbstractCommand<PN532InJumpForDEP, PN532InJumpForDEPResponse> {
    
    /**
     * <p>
     * Communication mode requested by the host controller.
     * </p>
     */
    public enum ActPass {
        /** 0x00 : Passive mode */
        Passive,
        /** 0x01 : Active Mode */
        Active
    }
    
    /**
     * <p>
     * Baud Rate to be used during the activation.
     * </p>
     */
    public enum BaudRate {
        /** 0x00 : 106 kbps */
        BR_106kbps,
        /** 0x01 : 212 kbps */
        BR_212kbps,
        /** 0x02 : 424 kbps */
        BR_424kbps
    }
    
    public static class PassiveInitiatorData {
        public PassiveInitiatorData(byte[] b) {
            this.b = b;
        }
        
        private byte[] b;
    }
    
    public static class NFCID3 {
        
    }
    
    public static class G {
        
    }
    
    public PN532InJumpForDEP(ActPass actPass, BaudRate baudRate, PassiveInitiatorData passiveInitiatorData) {
        this(actPass, baudRate, passiveInitiatorData, null, null);
    }
    
    public PN532InJumpForDEP(ActPass actPass, BaudRate baudRate, PassiveInitiatorData passiveInitiatorData, NFCID3 nfcid3, G g) {
        super(PN532InJumpForDEPResponse.class);
        this.actPass = actPass;
        this.baudrate = baudRate;
        this.passiveInitiatorData = passiveInitiatorData;
        this.nfcid3 = nfcid3;
        this.g = g;
    }
    
    public void transfer(ByteBuffer buffer) {
        buffer.put((byte) 0xD4);
        buffer.put((byte) 0x56);
        // *** ActPass ***
        switch (actPass) {
        case Active:
            buffer.put((byte) 0x00);
            break;
        case Passive:
            buffer.put((byte) 0x01);
            break;
        default:
        }
        // *** BR ***
        switch (baudrate) {
        case BR_106kbps:
            buffer.put((byte) 0x00);
            break;
        case BR_212kbps:
            buffer.put((byte) 0x01);
            break;
        case BR_424kbps:
            buffer.put((byte) 0x02);
            break;
        default:
        }
        // *** Next ***
        int next = 0x00;
        if (passiveInitiatorData != null)
            next |= 0x01;
        if (nfcid3 != null)
            next |= 0x02;
        if (g != null)
            next |= 0x04;
        buffer.put((byte) next);
        
        if (passiveInitiatorData != null)
            buffer.put(passiveInitiatorData.b);
    }
    public int getInstruction() { return 0x56; }
   
    public int getLength() {
        return 2 + 3 + (passiveInitiatorData == null ? 0 : passiveInitiatorData.b.length);
    }
    
    private ActPass actPass;
    private BaudRate baudrate;
    private PassiveInitiatorData passiveInitiatorData;
    private NFCID3 nfcid3;
    private G g;
}
