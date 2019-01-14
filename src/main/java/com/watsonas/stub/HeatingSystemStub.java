package com.watsonas.stub;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.watsonas.model.HeatingSystem;

@Component
@Profile("test")
public class HeatingSystemStub implements HeatingSystem {

	@Override
	public boolean turnDevice(boolean onOrOff) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDeviceOn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDeviceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getTemperature() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTargetTemperature() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAutomatic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAutomatic(boolean onOrOff) {
		// TODO Auto-generated method stub
		
	}



}
