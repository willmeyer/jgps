package com.willmeyer.jgps.nmea;

import java.text.ParseException;
import java.util.*;

class Sentence {

	private String sentence;
	private String fields[];
	private byte checksum;
	private boolean hasChecksum;

	Sentence(String s) throws ParseException {
		sentence = s;
		s.trim();
		chopSentence();
		for (int i = 0; i < fields.length; i++) {
			// System.out.print(" [" + i + "]= " + fields[i]);
		}
	}

	public String getTalkerID() throws ParseException {
		try {
			return fields[0].substring(1, 3);
		} catch (IndexOutOfBoundsException indexoutofboundsexception) {
			throw new ParseException(
					"NMEA0183: not a valid start of sentence: " + fields[0], 0);
		}
	}

	public String getSentenceType() throws ParseException {
		try {
			if (fields[0].startsWith("$P"))
				return fields[0].substring(1, fields[0].length());
			else
				return fields[0].substring(3, fields[0].length());
		} catch (IndexOutOfBoundsException indexoutofboundsexception) {
			throw new ParseException(
					"NMEA0183: not a valid start of sentence: " + fields[0], 0);
		}
	}

	public boolean isTrue(int i) throws ParseException {
		try {
			if (fields[i].equals("A"))
				return true;
			if (fields[i].equals("V"))
				return false;
		} catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
		}
		throw new ParseException("NMEA0183: index is not a boolean field", i);
	}

	public boolean isNorth(int i) throws ParseException {
		try {
			if (fields[i].equals("N"))
				return true;
			if (fields[i].equals("S"))
				return false;
		} catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
		}
		throw new ParseException("NMEA0183: index is not a North/South field",
				i);
	}

	public boolean isEast(int i) throws ParseException {
		try {
			if (fields[i].equals("E"))
				return true;
			if (fields[i].equals("W"))
				return false;
		} catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
		}
		throw new ParseException("NMEA0183: index is not a East/West field", i);
	}

	public Date getUTCTime(int i) throws ParseException {
		try {
			int j = Integer.parseInt(fields[i].substring(0, 2));
			int k = Integer.parseInt(fields[i].substring(2, 4));
			int l = Integer.parseInt(fields[i].substring(4, 6));
			int i1 = 0;
			if (fields[i].length() > 6) {
				float f = Float.valueOf(
						fields[i].substring(6, fields[i].length()))
						.floatValue();
				i1 = (int) f * 1000;
			}
			GregorianCalendar gregoriancalendar = new GregorianCalendar(
					TimeZone.getTimeZone("UTC"));
			gregoriancalendar.set(10, j);
			gregoriancalendar.set(12, k);
			gregoriancalendar.set(13, l);
			gregoriancalendar.set(14, i1);
			return gregoriancalendar.getTime();
		} catch (ArrayIndexOutOfBoundsException e) {
		} catch (StringIndexOutOfBoundsException e) {
		} catch (NumberFormatException e) {
		}
		throw new ParseException("NMEA0183: index is not a UTC time field", i);
	}

	public int getInteger(int i) throws ParseException {
		try {
			return Integer.parseInt(fields[i]);
		} catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
		} catch (NumberFormatException numberformatexception) {
		}
		throw new ParseException("NMEA0183: index is not an interger field", i);
	}

	public byte getByte(int i) throws ParseException {
		try {
			return Byte.parseByte(fields[i]);
		} catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
		} catch (NumberFormatException numberformatexception) {
		}
		throw new ParseException("NMEA0183: index is not an interger field", i);
	}

	public double getDouble(int i) throws ParseException {
		try {
			return Double.valueOf(fields[i]).doubleValue();
		} catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
		} catch (NumberFormatException numberformatexception) {
		}
		throw new ParseException("NMEA0183: index is not an double field", i);
	}

	public String getString(int i) throws ParseException {
		try {
			return fields[i];
		} catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
			throw new ParseException(
					"NMEA0183: getString(int) index too high!!", i);
		}
	}

	public int getNumberOfDataFields() {
		return fields.length;
	}

	public boolean isSet(int i) throws ParseException {
		try {
			return !fields[i].equals("");
		} catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
			throw new ParseException("NMEA0183: isSet(int) index too high!!", i);
		}
	}

	public boolean isChecksumBad() throws ParseException {
		return hasChecksum && computeChecksum() != checksum;
	}

	@SuppressWarnings("unchecked")
	private void chopSentence() throws ParseException {
		try {
			StringTokenizer stringtokenizer = new StringTokenizer(sentence,
					",", true);
			Vector vector = new Vector();
			String s;
			for (String s1 = ""; stringtokenizer.hasMoreTokens(); s1 = s) {
				s = stringtokenizer.nextToken();
				if (s.equals(",") && s1.equals(","))
					vector.addElement("");
				else if (!s.equals(","))
					vector.addElement(s);
			}

			fields = new String[vector.size()];
			vector.copyInto(fields);
			if (fields[fields.length - 1].indexOf('*') == -1) {
				hasChecksum = false;
			} else {
				String s2 = fields[fields.length - 1];
				fields[fields.length - 1] = s2.substring(0, s2.indexOf('*'));
				checksum = Byte.parseByte(s2.substring(s2.indexOf('*') + 1, s2
						.length()), 16);
				hasChecksum = true;
			}
		} catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
			throw new ParseException("NMEA0183: invalid data in checksum", -1);
		} catch (Exception exception) {
			throw new ParseException(
					"NMEA0183: Sentence is not a Valid NMEA0183 Sentence", 0);
		}
	}

	private byte computeChecksum() {
		byte byte0 = 0;
		for (int i = 1; i < sentence.length(); i++) {
			char c = sentence.charAt(i);
			if (c == '*')
				break;
			byte0 ^= c;
		}

		return byte0;
	}

	public String toString() {
		return sentence;
	}

}