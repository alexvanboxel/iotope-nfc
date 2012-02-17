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
 * This command is used to configure the different settings of the PN532 as described in
 * the input section of this command.
 * </p>
 * 
 * @author Alex Van Boxel <alex@vanboxel.be>
 * @see NXP PN532 User Manual - 7.3.1 RFConfiguration
 */
public class PN532RFConfiguration extends PN532AbstractCommand<PN532RFConfiguration, PN532RFConfigurationResponse> {
    
    public PN532RFConfiguration(int configItem, byte[] configData) {
        super(PN532RFConfigurationResponse.class);
        this.configItem = configItem;
        this.configData = configData;
    }
    
    public PN532RFConfiguration() {
        super(PN532RFConfigurationResponse.class);
        this.configItem = (byte) 0x05;
        // MaxRetryATR, MaxRetryPSL, MaxRetryPassiveActivation
        this.configData = new byte[]{(byte)0x00, (byte)0x00, (byte)0x50};
    }
    
    public void transfer(ByteBuffer buffer) {
        buffer.put((byte) 0xD4);
        buffer.put((byte) 0x32);
        // *** ConfigItem ***
        buffer.put((byte) configItem);
        // *** ConfigurationData ***
        buffer.put(configData);
    }
    
    public int getLength() {
        return 2 + 1 + configData.length;
    }
    
    @Override
    public String toString() {
        return ">> RFConfiguration {item:" + hex(configItem) +hex(configData);
    }

    private int configItem;
    private byte[] configData;
}
