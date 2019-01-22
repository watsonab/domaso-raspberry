package com.watsonas.model.grove;

import java.io.IOException;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveRelay;
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.watsonas.model.AbstractHeatingSystem;
import com.watsonas.model.HeatingSystem;
/*
 Connect Grove Temp & Humidity sensor to D7, Relay for heating system to D2
 */
@Component
@Profile("prod")
public class GroveHeatingSystem extends AbstractHeatingSystem implements HeatingSystem {
	
	private final Logger logger = LoggerFactory.getLogger( GroveHeatingSystem.class );
	
	@Autowired
	private GrovePi grovePi;
	
	@Value("${targetTemperature}")
	protected double targetTemperature = 23;

	public GroveHeatingSystem() {
		super();
	}
	
	@Override
	public boolean turnDevice(boolean onOrOff) {
		try {
			GroveRelay relay = new GroveRelay(grovePi, 2);
			relay.set(onOrOff);
			isHeatingSystemOn = onOrOff;
			return true;
		} catch (IOException io) {
			logger.error("Failed to turn heating system " + ( onOrOff ? "on" : "off" ), io);
			return false;
		}
	}

	@Override
	public String getDeviceName() {
		return "Grove Heating System";
	}
	
	@Override
	public double getTargetTemperature() {
		return targetTemperature;
	}	

	@Override
	public double getTemperature() {
		try {
			int tries = 0;
			while( tries++ < 3 ) {
				GroveTemperatureAndHumiditySensor dht = new GroveTemperatureAndHumiditySensor(grovePi, 7,
					GroveTemperatureAndHumiditySensor.Type.DHT11);
				double result = dht.get().getTemperature();
				if (!Double.isNaN(result) ) {
					return result;
				}
				Thread.sleep( 500 );
			}
		} catch (Exception io) {
			throw new RuntimeException(io);
		}
		throw new RuntimeException( "Failed to get temperature");
	}
	

	
}
