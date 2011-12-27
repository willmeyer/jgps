package com.willmeyer.jgps;

/**
 * The basic communications interface to a GPS receiver device.
 */
public interface GpsReceiver {

	public void setListener(GpsListener listener);
	
	public interface GpsListener {
		
		public void onPositionUpdate(String pos);
		
	}
}
