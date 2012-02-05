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

package org.iotope.nfc;

import java.util.List;

import javax.smartcardio.Card;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

import org.iotope.nfc.reader.ReaderChannel;
import org.iotope.nfc.reader.ReaderConnection;

public class NfcTest implements Runnable {

	static public void main(String... atrg) {
		new NfcTest().run();
	}

	public void run() {
		try {
			TerminalFactory factory = TerminalFactory.getDefault();

			// factory.terminals().waitForChange();
			List<CardTerminal> terminals = factory.terminals().list();
			if (terminals == null || terminals.isEmpty()) {
			}

			for (CardTerminal terminal : terminals) {
				Card card = terminal.connect("*");
				ReaderConnection connection = new ReaderConnection();
				ReaderChannel channel = new ReaderChannel(connection,
						card.getBasicChannel());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
