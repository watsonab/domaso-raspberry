package com.watsonas.pi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.watsonas.model.Camera;

@Component
@Profile("prod")
public class PiCamera implements Camera {
	
	Logger logger = LoggerFactory.getLogger(PiCamera.class);

	@Value("${imageDir}")
	private String imageDir;
	
	private ProcessBuilder pb;
	
	public File takePicture(String pictureName) {

		List<String> command = new ArrayList<>();
		command.add("raspistill");
		command.add("-o");
		command.add(imageDir + File.separator + pictureName );
		command.add("-w");
		command.add("1440");
		command.add("-h");
		command.add("1080");
		command.add("-t1");
		command.add("1");
		command.add("-rot");
		command.add("180");
//		command.add("-roi");
//		command.add("'0,0.125,0.99,0.75'");

		Process p;
		pb = new ProcessBuilder(command);

		logger.info( "Running " + pb.command().toString() );
		
		try {
			p = pb.start();
			StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), logger::info);
	 		StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), logger::error);
	 		Thread outputThread = new Thread(outputGobbler);
	 		outputThread.start();
	 		Thread errorThread = new Thread(errorGobbler);
	 		errorThread.start();
			logger.info("Waiting for camera..." );
			p.waitFor();
			outputThread.join(500);
			errorThread.join(500);
			p.destroyForcibly();
			return new File(imageDir + File.separator + pictureName);
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

}
