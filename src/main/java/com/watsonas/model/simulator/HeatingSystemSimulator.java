package com.watsonas.model.simulator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.watsonas.model.AbstractHeatingSystem;
import com.watsonas.model.HeatingSystem;

@Component
@Profile("dev")
public class HeatingSystemSimulator extends AbstractHeatingSystem implements HeatingSystem {

	private double temperature;
	protected double targetTemperature = 23;
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	public HeatingSystemSimulator(  ) {
		super();
		executor.scheduleAtFixedRate( ()->temperature = ++temperature % 40, 1L, 10L, TimeUnit.SECONDS );
	}
	
	public String getDeviceName() {
		return "Simulated Heating System";
	}
	
	@Override
	public boolean turnDevice(boolean onOrOff) {
		isHeatingSystemOn = onOrOff;
		return true;
	}
	
	@Override
	public double getTemperature() {
		return temperature;
	}	

	@Override
	public double getTargetTemperature() {
		return targetTemperature;
	}	

}