package com.watsonas.stub;

import java.io.File;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.watsonas.model.Camera;

@Component
@Profile("test")
public class CameraStub implements Camera {

	@Override
	public File takePicture(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
