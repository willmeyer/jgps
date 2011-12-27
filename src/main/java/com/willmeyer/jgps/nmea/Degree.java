package com.willmeyer.jgps.nmea;

public class Degree {

	private int degrees;
	private int minutes;
	private double seconds;

	public Degree(int i, int j, double d) {
		degrees = i;
		minutes = j;
		seconds = d;
		if (j > 60)
			throw new IllegalArgumentException(
					"\264There can not be more than 60 minutes in a degree");
		if (d > 60D)
			throw new IllegalArgumentException(
					"\264There can not be more than 60 seconds in a minute");
		else
			return;
	}

	public int getDegrees() {
		return degrees;
	}

	public int getMinutes() {
		return minutes;
	}

	public double getSeconds() {
		return seconds;
	}

	public static Degree parseDDMM_MM(double d) {
		int i = (int) (d / 100D);
		int j = (int) (d - (double) (i * 100));
		double d1 = (d - (double) (i * 100) - (double) j) * 60D;
		return new Degree(i, j, d1);
	}

	public static Degree parseDDMM_SS(double d) {
		int i = (int) (d / 100D);
		int j = (int) (d - (double) (i * 100));
		double d1 = (d - (double) (i * 100) - (double) j) * 100D;
		return new Degree(i, j, d1);
	}

	public double getAsDegree() {
		return (double) degrees + (double) minutes / 60D + seconds / 3600D;
	}

	public double getAsMinutes() {
		return (double) (degrees * 60 + minutes) + seconds / 60D;
	}

}
