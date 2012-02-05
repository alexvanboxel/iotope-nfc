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
