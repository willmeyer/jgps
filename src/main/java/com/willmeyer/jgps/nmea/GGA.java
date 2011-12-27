package com.willmeyer.jgps.nmea;

import java.text.ParseException;
import java.util.Date;

public class GGA implements ParsedSentence {

	public static final String SENTENCE_TYPE = "GGA";
	private Date UTCTime;
	private NMEA0183Coordinate pos;
	private byte GPSQuality;
	public static final byte NO_FIX = 0;
	public static final byte GPS_FIX = 1;
	public static final byte DGPS_FIX = 2;
	private byte numberOfSatellitesInUse;
	private double horizontalDilutionOfPrecision;
	private double antennaAltitudeMeters;
	private double geoidalSeparationMeters;
	private double ageOfDifferentialGPSDataSeconds;
	private int differentialReferenceStationID;

	protected GGA(Sentence sentence) throws ParseException {
		GPSQuality = 0;
		numberOfSatellitesInUse = 0;
		horizontalDilutionOfPrecision = 0.0D;
		antennaAltitudeMeters = 0.0D;
		geoidalSeparationMeters = 0.0D;
		ageOfDifferentialGPSDataSeconds = 0.0D;
		differentialReferenceStationID = 0;
		if (!sentence.getSentenceType().equals("GGA"))
			throw new ParseException("NMEA0183 - GGA: Expected GGA mnemonic", 0);
		if (sentence.isChecksumBad())
			throw new ParseException("NMEA0183 - GGA: Bad checksum: ", 0);
		UTCTime = sentence.getUTCTime(1);
		double d = sentence.getDouble(2);
		if (!sentence.isNorth(3))
			d = -d;
		double d1 = sentence.getDouble(4);
		if (!sentence.isEast(5))
			d1 = -d1;
		pos = new NMEA0183Coordinate(Degree.parseDDMM_MM(d), Degree
				.parseDDMM_MM(d1));
		GPSQuality = sentence.getByte(6);
		numberOfSatellitesInUse = sentence.getByte(7);
		horizontalDilutionOfPrecision = sentence.getDouble(8);
		antennaAltitudeMeters = sentence.getDouble(9);
		if (!sentence.getString(10).equals("M"))
			throw new ParseException(
					"NMEA0183 - GGA: Expected Unit of meters.", 10);
		if (sentence.isSet(11)) {
			geoidalSeparationMeters = sentence.getDouble(11);
			if (!sentence.getString(12).equals("M"))
				throw new ParseException(
						"NMEA0183 - GGA: Expected Unit of meters.", 12);
		}
		if (sentence.isSet(13))
			ageOfDifferentialGPSDataSeconds = sentence.getDouble(13);
		differentialReferenceStationID = sentence.getInteger(14);
	}

	public Date getDateofFix() {
		return UTCTime;
	}

	public NMEA0183Coordinate getLocationOfFix() {
		return pos;
	}

	public byte getQualityOfFix() {
		return GPSQuality;
	}

	public byte getNumberOfSatellitesInUse() {
		return numberOfSatellitesInUse;
	}

	public double getHorizontalDilutionOfPrecision() {
		return horizontalDilutionOfPrecision;
	}

	public double getAntennaAltitudeMeters() {
		return antennaAltitudeMeters;
	}

	public double getGeoidalSeparationMeters() {
		return geoidalSeparationMeters;
	}

	public double getAgeOfDifferentialGPSDataSeconds() {
		return ageOfDifferentialGPSDataSeconds;
	}

	public int getDifferentialReferenceStationID() {
		return differentialReferenceStationID;
	}

	public String inPlainEnglish() {
		String s = "At ";
		s = s + UTCTime.toString();
		s = s + "you were at ";
		s = s + pos.getLatitude().getAsDegree() + "N "
				+ pos.getLongitude().getAsDegree() + "E";
		s = s + ", ";
		switch (GPSQuality) {
		case 1: // '\001'
			s = s + "based upon a GPS fix ";
			break;

		case 2: // '\002'
			s = s + "based upon a differential GPS fix ";
			break;

		default:
			s = s + "a GPS fix was not available ";
			break;
		}
		s = s + numberOfSatellitesInUse + " satellites are in use.";
		return s;
	}

	public String getSentenceType() {
		return "GGA";
	}

	public int getSentenceID() {
		return 1006;
	}

}