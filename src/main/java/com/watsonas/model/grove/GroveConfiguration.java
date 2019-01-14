package com.watsonas.model.grove;

import java.io.IOException;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.pi4j.GrovePi4J;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class GroveConfiguration {
    
    @Bean
    public GrovePi grovePi() {
    	try {
    		return new GrovePi4J();
    	}
    	catch( IOException io ) {
    		throw new RuntimeException( io );
    	}
    }
}
