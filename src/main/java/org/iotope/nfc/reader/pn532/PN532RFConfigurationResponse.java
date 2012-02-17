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

/**
 * <p>
 * This command is used to configure the different settings of the PN532 as described in
 * the input section of this command.
 * </p>
 * 
 * @author Alex Van Boxel <alex@vanboxel.be>
 * @see NXP PN532 User Manual - 7.3.1 RFConfiguration
 */
public class PN532RFConfigurationResponse extends PN532AbstractResponse<PN532RFConfiguration> {
    
    public PN532RFConfigurationResponse(PN532RFConfiguration command, ByteBuffer buffer) {
        super(command, buffer);
        checkInstruction(0x33, buffer.get());
    }
    
    @Override
    public String toString() {
        return "<< RFConfiguration";
    }
}
