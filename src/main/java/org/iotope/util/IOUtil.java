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
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {
    
    public static int bitset(int iByte, int bit) {
        return iByte | (1 << bit);
    }
    
    public static String hex(byte[] array) {
    	return hex(array,array.length);
    }
    
    public static String hex(byte[] array,int length) {
        if (array == null)
            return "[---]";
        String result = "[";
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                result += " ";
            }
            result += hex(array[i]);
        }
        result += "]";
        return result;
    }
    
    public static String hexbin(byte[] array) {
        if (array == null)
            return "";
        String result = "";
        for (int i = 0; i < array.length; i++) {
            result += hex(array[i]);
        }
        return result;
    }
    
    public static String hex(int b) {
        String result = Integer.toHexString(b);
        if (result.length() == 1)
            result = "0" + result;
        else
            result = result.substring(result.length() - 2);
        return result;
    }
    
    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte buffer[] = new byte[512000];
        int length;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();
    }
}
