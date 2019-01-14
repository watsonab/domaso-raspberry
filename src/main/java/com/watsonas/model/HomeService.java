package com.watsonas.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.watsonas.home.Home;

@Component
public class HomeService implements Home {

	@Autowired
	private HeatingSystem heatingDevice;
	
	public HomeService() {
	}
	
	public HeatingSystem getHeatingSystem() {
		return heatingDevice;
	};
	
}
