package com.watsonas.model.simulator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.watsonas.model.Camera;

@Component
@Profile("dev")
public class CameraSimulator implements Camera {
	
	Logger logger = LoggerFactory.getLogger(CameraSimulator.class); 

	@Value("${imageDir}")
	private String saveDir;
	
	@Override
	public File takePicture(String name) {
		
		File simPic = new File(getClass().getResource("/static/barb.jpg").getFile());
		Path sourcePath = simPic.toPath();
		Path targetPath = Paths.get(saveDir).resolve(name);
		try {
			Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING );
			logger.info("Copied " + sourcePath + " to "  + targetPath );
		}
		catch( IOException ioException ) {
			throw new RuntimeException( ioException );
		}
		
		return targetPath.toFile();
	}

}
