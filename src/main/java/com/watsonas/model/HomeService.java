package com.watsonas.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.watsonas.home.Home;

@Component
public class HomeService implements Home {

	@Autowired
	private HeatingSystem heatingDevice;

	@Autowired
	private Camera camera;
	
	public HeatingSystem getHeatingSystem() {
		return heatingDevice;
	};

	public Camera getCamera() {
		return camera;
	};

}
