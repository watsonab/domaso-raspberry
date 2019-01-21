/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.watsonas.tomcat.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.watsonas.home.Home;
import com.watsonas.model.HeatingSystem;

@Controller
public class HomeController {

	private final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private Home home;

	// http://localhost:8081/livingRoomTemperature
	@RequestMapping("/livingRoomTemperature")
	@ResponseBody
	public String currentLivingRoomTemperature() {
		HeatingSystem heatingSystem = home.getHeatingSystem();
		return "" + heatingSystem.getTemperature();
	}

	// http://localhost:8081/getById/95?id2=55
	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getById(@PathVariable String id, @RequestParam("id2") String id2) {
		return new ResponseEntity<String>("GET Response : " + id + " " + id2, HttpStatus.OK);
	}

	// http://localhost:8081/heatingDeviceState
	@RequestMapping("/heatingSystemState")
	@ResponseBody
	public String heatingDeviceState() {
		HeatingSystem heatingSystem = home.getHeatingSystem();
		return heatingSystem.isDeviceOn() ? "on" : "off";
	}
	
	// http://localhost:8081/heatingDeviceControl
	@RequestMapping("/heatingSystemControl")
	@ResponseBody
	public String heatingDeviceControl() {
		HeatingSystem heatingSystem = home.getHeatingSystem();
		return heatingSystem.isAutomatic() ? "auto" : "off";
	}

	// http://localhost:8081/setHeatingSystemControl/on
	@RequestMapping(value = "/setHeatingSystemControl/{requiredState}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> setHeatingSystemState(@PathVariable String requiredState) {
		HeatingSystem heatingSystem = home.getHeatingSystem();
		
		logger.info("Received call to /setHeatingSystemControl/" + requiredState );

		switch (requiredState) {
		case "auto":
			heatingSystem.setAutomatic(true);
			return new ResponseEntity<String>("auto", HttpStatus.OK);

		case "off":
			heatingSystem.setAutomatic(false);
			if (heatingSystem.isDeviceOn()) {
				heatingSystem.setAutomatic(false);
			}
			return new ResponseEntity<String>("off", HttpStatus.OK);

		default:
			return new ResponseEntity<String>("Sorry, I don't understand what " + requiredState + " means",
					HttpStatus.BAD_REQUEST);
		}
	}
}
