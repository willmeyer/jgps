package com.willmeyer.jgps;


public class GpsReceiverApp implements GpsReceiver.GpsListener {

	NmeaSerialGpsDeviceV3 gps = null;
	
	public void onPositionUpdate(String pos) {
		System.out.println("Position update: " + pos);
	}

	public GpsReceiverApp(String comPort) throws Exception {
		System.out.println("Opening port " + comPort);
		gps = new NmeaSerialGpsDeviceV3(comPort);
		gps.connect();
		gps.setListener(this);
	}
	
	public static void usage() {
		System.out.println("Usage: GpsReceiverApp [comport]");
	}
	
	public static void main(String[] params) {
		if (params.length != 1) {
			usage();
			return;
		}
		String comPort = params[0];
		try {
			new GpsReceiverApp(comPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
