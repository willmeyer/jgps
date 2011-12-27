package com.willmeyer.jgps.nmea;

import java.io.*;
import java.text.ParseException;

import org.slf4j.*;

public class NmeaStreamParser implements Runnable {

	protected Logger logger = LoggerFactory.getLogger("nmea");

	private boolean running;
	private BufferedReader br;
	private ParserListener callbackee;
	private Thread runner;

	public interface ParserListener {

		public abstract void parserOutput(ParsedSentence parsedsentence);
	}

	public NmeaStreamParser(InputStream inputstream,
			ParserListener parserlistener) {
		running = true;
		if (parserlistener == null) {
			throw new IllegalArgumentException(
					"Parser callbackee may not be null");
		} else {
			callbackee = parserlistener;
			br = new BufferedReader(new InputStreamReader(inputstream), 100);
			runner = new Thread(this);
			runner.start();
			return;
		}
	}

	public void close() {
		if (running) {
			running = false;
			try {
				runner.interrupt();
				br.close();
			} catch (Exception exception) {
				logger.error("Exception from thread loop: {}", exception);
			}
			br = null;
			callbackee = null;
		}
	}

	/**
	 * The main parsing loop -- reads a sentence at a time for as long as it
	 * can, or until stopped.
	 */
	public void run() {
		try {
			for (String s = br.readLine(); running && s != null; s = br
					.readLine()) {
				logger.debug("Read line from stream: {}", s);
				Object obj2 = null;
				try {
					Sentence sentence = new Sentence(s);
					int i = Mappings.getSentenceID(sentence.getSentenceType());
					switch (i) {
					case 1006:
						obj2 = new GGA(sentence);
						break;

					case 1007:
						obj2 = new GLL(sentence);
						break;

					default:
						logger.debug(
								"Encountered unknown sentence ({}), ignoring.",
								sentence.getSentenceType());
						break;
					}
					notifyListener(((ParsedSentence) (obj2)));
				} catch (ParseException parseexception) {
					logger.error("Error parsing sentence: {} (offset {})",
							parseexception.getMessage(), parseexception
									.getErrorOffset());
				}
			}
		} catch (IOException ioexception) {
			logger.error("Error occured reading from input Stream: {}",
					ioexception);
		}
	}

	private void notifyListener(ParsedSentence parsedsentence) {
		if (parsedsentence == null)
			return;
		try {
			callbackee.parserOutput(parsedsentence);
		} catch (Exception exception) {
			logger.error("Exception occured calling ParserListener: {}",
					exception);
		}
	}

}