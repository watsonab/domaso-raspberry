package com.watsonas.stub;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.watsonas.model.HeatingSystem;

@Component
@Profile("test")
public class HeatingSystemStub implements HeatingSystem {

	@Override
	public boolean turnDevice(boolean onOrOff) {
		return false;
	}

	@Override
	public boolean isDeviceOn() {
		return false;
	}

	@Override
	public String getDeviceName() {
		return null;
	}

	@Override
	public double getTemperature() {
		return 0;
	}

	@Override
	public double getTargetTemperature() {
		return 0;
	}

	@Override
	public boolean isAutomatic() {
		return false;
	}

	@Override
	public void setAutomatic(boolean onOrOff) {
		
	}

}
