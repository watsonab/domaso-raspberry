package com.watsonas.model;

public interface Device {
	boolean turnDevice( boolean onOrOff );
	boolean isDeviceOn();
	String getDeviceName();
}
