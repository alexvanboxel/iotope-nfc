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

package org.iotope.nfc.reader.pn532.struct;

import java.nio.ByteBuffer;

import org.iotope.util.IOUtil;

public final class PN532Status {
    
    public PN532Status(ByteBuffer buffer) {
        int status = buffer.get();
        error = 0x3F & status;
        mi = (0x40 & status) == 0x40;
        nad = (0x80 & status) == 0x80;
    }
    
    public boolean hasError() {
        return error > 0;
    }
    
    public boolean hasMoreInformation() {
        return mi;
    }
    
    public boolean hasNodeAddress() {
        return nad;
    }
    
    @Override
    public String toString() {
        /*
        Time Out, the target has not answered 0x01
        A CRC error has been detected by the CIU 0x02
        A Parity error has been detected by the CIU 0x03
        During an anti-collision/select operation (ISO/IEC14443-3
        Type A and ISO/IEC18092 106 kbps passive mode), an
        erroneous Bit Count has been detected
        0x04
        Framing error during Mifare operation 0x05
        An abnormal bit-collision has been detected during bit wise
        anti-collision at 106 kbps
        0x06
        Communication buffer size insufficient 0x07
        RF Buffer overflow has been detected by the CIU (bit
        BufferOvfl of the register CIU_Error)
        0x09
        In active communication mode, the RF field has not been
        switched on in time by the counterpart (as defined in NFCIP-1
        standard)
        0x0A
        RF Protocol error (cf. Error! Reference source not found.,
        description of the CIU_Error register)
        0x0B
        Temperature error: the internal temperature sensor has
        detected overheating, and therefore has automatically
        switched off the antenna drivers
        0x0D
        Internal buffer overflow 0x0E
        Invalid parameter (range, format, …) 0x10

        DEP Protocol: The PN532 configured in target mode does not
        support the command received from the initiator (the
        command received is not one of the following: ATR_REQ,
        WUP_REQ, PSL_REQ, DEP_REQ, DSL_REQ, RLS_REQ
        Error! Reference source not found.).
        0x12
        DEP Protocol, Mifare or ISO/IEC14443-4: The data format
        does not match to the specification.
        Depending on the RF protocol used, it can be:
        • Bad length of RF received frame,
        • Incorrect value of PCB or PFB,
        • Invalid or unexpected RF received frame,
        • NAD or DID incoherence.
        0x13
        Mifare: Authentication error 0x14
        ISO/IEC14443-3: UID Check byte is wrong 0x23
        DEP Protocol: Invalid device state, the system is in a state
        which does not allow the operation
        0x25
        Operation not allowed in this configuration (host controller
        interface)
        0x26
        This command is not acceptable due to the current context of
        the PN532 (Initiator vs. Target, unknown target number,
        Target not in the good state, …)
        0x27
        The PN532 configured as target has been released by its
        initiator 0x29
        PN532 and ISO/IEC14443-3B only: the ID of the card does
        not match, meaning that the expected card has been
        exchanged with another one.
        0x2A
        PN532 and ISO/IEC14443-3B only: the card previously
        activated has disappeared. 0x2B
        Mismatch between the NFCID3 initiator and the NFCID3
        target in DEP 212/424 kbps passive. 0x2C
        An over-current event has been detected 0x2D
        NAD missing in DEP frame 0x2E         
         */
        // TODO Auto-generated method stub
        return "[Status error:" + error + " NAD:" + nad + " MI:" + mi + "]";
    }
    
    private boolean nad;
    private boolean mi;
    private int error;
}
