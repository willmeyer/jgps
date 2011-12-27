package com.willmeyer.jgps;

import java.io.*;

import alipes.positioning.NMEA0183.*;

import org.slf4j.*;

import com.willmeyer.jrs232.*;

/**
 * Represents a NMEA0183-compatible device that connects via a simple serial port connection.  
 * 
 * V3 implementation uses the Alipes NMEA0183 parser.
 */
public final class NmeaSerialGpsDeviceV1 extends Rs232Device implements GpsReceiver {

	protected GpsListener listener = null;
	protected final Logger logger = LoggerFactory.getLogger(NmeaSerialGpsDeviceV1.class);

	public void setListener(GpsListener listener) {
		this.listener = listener;
	}

	public NmeaSerialGpsDeviceV1(String comPortName) throws Exception {
		super(comPortName);
	}
	
	protected NmeaParser parser = null;
	
	/**
	 * Adds parser start to the basic connect process.
	 */
	@Override
	public void connect() throws Exception {
		super.connect();
		parser = new NmeaParser();
	}

	public class NmeaParser implements ParserListener {

		public NmeaParser() throws IOException {
			new Parser(inStream, this);
		}

		/** 
		 * Handle notification of an event from the parser. 
		 *
		 */
		public void parserOutput(ParsedSentence parsedSentence) {
			
			NMEA0183Coordinate pos = null;
			switch (parsedSentence.getSentenceID()) {
				case Mappings.GLL:
					GLL sentenceGLL = (GLL) parsedSentence;
					if (sentenceGLL.isDataValid()) {
						pos = sentenceGLL.getLocationOfFix();
					}
					break;
				case Mappings.GGA:
					GGA sentenceGGA = (GGA) parsedSentence;
					if (sentenceGGA.getQualityOfFix() != GGA.NO_FIX) {
						pos = sentenceGGA.getLocationOfFix();
					}
					break;
				default:
					System.err.println("GPSDevice, not handling ParsedSentence: "
							+ parsedSentence.getSentenceType() + " "
							+ parsedSentence.getSentenceID());
					break;
			}
			if (pos != null) {
				dispatchPosition(pos);
			}
		}
		
		private void dispatchPosition(NMEA0183Coordinate c) {
			logger.info("Received position update: {}", formatCoordSimple(c));
			if (listener != null) {
				String posStr = formatCoordSimple(c);
				listener.onPositionUpdate(posStr);
			}
		}
	}

	/**
	 * Gives a string in the form: 
	 * 
	 *   "<degress lat> / <degrees lon>"
	 */
	public String formatCoordSimple(NMEA0183Coordinate coord) {
		double lat = coord.getLatitude().getAsDegree();
		double lon = coord.getLongitude().getAsDegree();
		String str = lat + " / " + lon;
		return str;
	}
}
