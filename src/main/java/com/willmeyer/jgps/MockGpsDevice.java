package com.willmeyer.jgps;

/**
 * A fake device, useful for testing.  All it does is send fake position updates.
 */
public class MockGpsDevice implements GpsReceiver {

	protected GpsListener listener = null;
	
	public void setListener(GpsListener listener) {
		this.listener = listener;
	}
	
	public MockGpsDevice() {
		Updater updater = new Updater();
		updater.setDaemon(true);
		updater.start();
	}
	
	protected class Updater extends Thread {
		protected boolean running = true;
		
		public void run() {
			while (running) {
				String posStr = "40.7552 / -73.9869"; 
				if (listener != null)
					listener.onPositionUpdate(posStr);
				try {
					sleep(1000);
				} catch (Exception e) {}
			}
		}
	}
}
