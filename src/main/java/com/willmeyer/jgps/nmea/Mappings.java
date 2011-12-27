package com.willmeyer.jgps.nmea;

import java.util.Hashtable;

import org.slf4j.*;

public class Mappings {

	protected static Logger logger = LoggerFactory.getLogger(Mappings.class);
	
	public Mappings() {
	}

	public static int getSentenceID(String s) {
		Integer integer = (Integer) mappings.get(s);
		if (integer == null) {
			logger.debug("Unable to find sentence type \"{}\" in Hashtable", s);
			return -1;
		} else {
			return integer.intValue();
		}
	}

	private static final Hashtable<String, Integer> mappings;
	
	public static final int APB = 1001;
	public static final int BOD = 1002;
	public static final int BWC = 1003;
	public static final int BWR = 1004;
	public static final int DBT = 1005;
	public static final int GGA = 1006;
	public static final int GLL = 1007;
	public static final int GSA = 1008;
	public static final int GSV = 1009;
	public static final int HDM = 1010;
	public static final int HSC = 1011;
	public static final int MTW = 1012;
	public static final int R00 = 1013;
	public static final int RMB = 1014;
	public static final int RMC = 1015;
	public static final int RTE = 1016;
	public static final int VHW = 1017;
	public static final int VWR = 1018;
	public static final int VTG = 1019;
	public static final int WCV = 1020;
	public static final int WDC = 1021;
	public static final int WDR = 1022;
	public static final int WPL = 1023;
	public static final int XTE = 1024;
	public static final int XTR = 1025;

	static {
		mappings = new Hashtable<String, Integer>();
		mappings.put("APB", new Integer(1001));
		mappings.put("BOD", new Integer(1002));
		mappings.put("BWC", new Integer(1003));
		mappings.put("BWR", new Integer(1004));
		mappings.put("DBT", new Integer(1005));
		mappings.put("GGA", new Integer(1006));
		mappings.put("GLL", new Integer(1007));
		mappings.put("GSA", new Integer(1008));
		mappings.put("GSV", new Integer(1009));
		mappings.put("HDM", new Integer(1010));
		mappings.put("HSC", new Integer(1011));
		mappings.put("MTW", new Integer(1012));
		mappings.put("R00", new Integer(1013));
		mappings.put("RMB", new Integer(1014));
		mappings.put("RMC", new Integer(1015));
		mappings.put("RTE", new Integer(1016));
		mappings.put("VHW", new Integer(1017));
		mappings.put("VWR", new Integer(1018));
		mappings.put("VTG", new Integer(1019));
		mappings.put("WCV", new Integer(1020));
		mappings.put("WDC", new Integer(1021));
		mappings.put("WDR", new Integer(1022));
		mappings.put("WPL", new Integer(1023));
		mappings.put("XTE", new Integer(1024));
		mappings.put("XTR", new Integer(1025));
	}

}
