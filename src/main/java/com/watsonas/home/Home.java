package com.watsonas.home;

import com.watsonas.model.Camera;
import com.watsonas.model.HeatingSystem;

public interface Home {
	HeatingSystem getHeatingSystem();
	Camera getCamera();
}
