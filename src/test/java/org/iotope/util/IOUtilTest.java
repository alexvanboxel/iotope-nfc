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

package org.iotope.util;

import java.io.IOException;
import java.nio.charset.Charset;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.io.Resources;

public class IOUtilTest {
    
    @Test
    public void orBit0() {
        Assert.assertEquals(1, IOUtil.bitset(0, 0));
    }
    
    @Test
    public void orBit7() {
        Assert.assertEquals(128, IOUtil.bitset(0, 7));
    }
    
    @Test
    public void orBit6() {
        Assert.assertEquals(64, IOUtil.bitset(0, 6));
    }
    
    @Test
    public void bin2hex() throws IOException {
        byte[] dataCompare = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x07, (byte) 0x80, (byte) 0x69, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
        
        String dataAsText = Resources.toString(getClass().getResource("bin2hex.txt"), Charset.forName("utf8"));
        byte[] dataAsBinary = IOUtil.bin2hex(dataAsText);
        
        Assert.assertEquals(IOUtil.hex(dataCompare), IOUtil.hex(dataAsBinary));
    }
}
