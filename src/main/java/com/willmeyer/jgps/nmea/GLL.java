package com.willmeyer.jgps.nmea;

import java.text.ParseException;
import java.util.Date;

public class GLL implements ParsedSentence {

	public static final String SENTENCE_TYPE = "GLL";
	private Date UTCTime;
	private NMEA0183Coordinate pos;
	private boolean dataValid;

	protected GLL(Sentence sentence) throws ParseException {
		dataValid = false;
		if (!sentence.getSentenceType().equals("GLL"))
			throw new ParseException("NMEA0183 - GLL: Expected GLL mnemonic", 0);
		if (sentence.isChecksumBad())
			throw new ParseException("NMEA0183 - GLL: Bad checksum", 15);
		double d = sentence.getDouble(1);
		if (!sentence.isNorth(2))
			d = -d;
		double d1 = sentence.getDouble(3);
		if (!sentence.isEast(4))
			d1 = -d1;
		pos = new NMEA0183Coordinate(Degree.parseDDMM_MM(d), Degree
				.parseDDMM_MM(d1));
		UTCTime = sentence.getUTCTime(5);
		dataValid = sentence.isTrue(6);
	}

	public Date getDateofFix() {
		return UTCTime;
	}

	public boolean isDataValid() {
		return dataValid;
	}

	public NMEA0183Coordinate getLocationOfFix() {
		return pos;
	}

	public String inPlainEnglish() {
		String s = "At ";
		s = s + UTCTime.toString();
		s = s + " UTC, you were at ";
		s = s + " !!!TODO - Add Position information!!! ";
		s = s + ", ";
		if (dataValid)
			s = s + " Data is valid";
		else
			s = s + " Data is valid";
		return s;
	}

	public String getSentenceType() {
		return "GLL";
	}

	public int getSentenceID() {
		return 1007;
	}

}
