package com.watsonas.model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class AbstractHeatingSystem implements HeatingSystem {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	protected boolean isHeatingSystemOn;
	protected boolean isAutomatic;

	protected final double hysterisisThreshold = 2;

	public AbstractHeatingSystem() {
		// check temp every minute
		executor.scheduleAtFixedRate(new AutomaticTemperatureTask(), 0, 1, TimeUnit.MINUTES);
	}

	@Override
	abstract public double getTemperature();

	@Override
	abstract public double getTargetTemperature();

	@Override
	abstract public boolean turnDevice(boolean onOrOff);

	@Override
	public boolean isDeviceOn() {
		return isHeatingSystemOn;
	}

	@Override
	public boolean isAutomatic() {
		return isAutomatic;
	}

	@Override
	public void setAutomatic(boolean onOrOff) {
		isAutomatic = onOrOff;
		if (!isAutomatic) {
			turnDevice(false);
		} else {
			executor.submit(new AutomaticTemperatureTask());
		}
		logger.info("Set automatic control to " + (onOrOff ? "on" : "off"));
	}

	public class AutomaticTemperatureTask implements Runnable {
		@Override
		public void run() {
			if (!isAutomatic)
				return;
			try {
				double roomTemperature = getTemperature();
				if (isHeatingSystemOn) {
					if (roomTemperature >= getTargetTemperature()) {
						logger.info("Room temperature is up to " + roomTemperature + ", target is "
								+ getTargetTemperature() + " so turning the heating off");
						turnDevice(false);
					}
				} else {
					if (roomTemperature < getTargetTemperature() - hysterisisThreshold) {
						logger.info("Room temperature is down to " + roomTemperature + ", target is "
								+ getTargetTemperature() + " so turning the heating on");
						turnDevice(true);
					}
				}
			} catch (Throwable t) {
				logger.error("Failed to get temperature", t);
			}
		}
	}
}
