# jGPS

A Java interface for communicating with GPS receiver devices.  The library includes a generic interface as well as
specific implementations for devices exposing the NMEA protocol over an RS-232 connection (very common).

## Requirements & Setup

- A connected NMEA-compatible GPS device via a (real or virtual) serial port
- The [jRS232 package](http://www.github.com/willmeyer/jrs232) and its `com.willmeyer.jrs232`, with its install requirements

## Using the Library

See `com.willmeyer.jgps.nmea` for different device implementations.
