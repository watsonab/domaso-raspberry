package com.watsonas.model;

public interface HeatingSystem extends Device {
	public double getTemperature();
	public double getTargetTemperature();
	public boolean isAutomatic();
	public void setAutomatic(boolean onOrOff);
	
}
