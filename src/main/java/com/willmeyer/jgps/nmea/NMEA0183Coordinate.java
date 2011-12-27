package com.willmeyer.jgps.nmea;

public class NMEA0183Coordinate {

	protected Degree latitude;
	protected Degree longitude;

	public NMEA0183Coordinate(Degree degree, Degree degree1) {
		latitude = degree;
		longitude = degree1;
	}

	public Degree getLongitude() {
		return longitude;
	}

	public void setLongitude(Degree degree) {
		longitude = degree;
	}

	public Degree getLatitude() {
		return latitude;
	}

	public void setLatitude(Degree degree) {
		latitude = degree;
	}

}
