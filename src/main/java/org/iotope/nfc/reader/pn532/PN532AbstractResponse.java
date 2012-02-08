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

import static com.google.common.base.Preconditions.checkState;

import java.nio.ByteBuffer;

import org.iotope.nfc.reader.AbstractResponse;
import org.iotope.nfc.reader.ReaderCommand;

public abstract class PN532AbstractResponse<COMMAND extends ReaderCommand> extends AbstractResponse<COMMAND> {
    
    public PN532AbstractResponse(COMMAND command, ByteBuffer buffer) {
        super(command);
        checkState(buffer.get() == 0xD5, "Illegal PN532 response state, response always start with 0xD5");
    }
    
    protected void checkInstruction(int expected, int actual) {
        checkState(expected == actual, "Expected instruction %x , got %x", expected, actual);
        
    }
    
}
