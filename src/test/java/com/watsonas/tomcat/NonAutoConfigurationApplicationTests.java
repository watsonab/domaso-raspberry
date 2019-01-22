/*
 * Copyright 2012-2014 the original author or authors.
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

package com.watsonas.tomcat;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.watsonas.HomeApplication;
import com.watsonas.tomcat.NonAutoConfigurationApplicationTests.NonAutoConfigurationSampleTomcatApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "test")
@SpringApplicationConfiguration(classes = NonAutoConfigurationSampleTomcatApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
@DirtiesContext
public class NonAutoConfigurationApplicationTests {

	@Value("${local.server.port}")
	private int port;

	@Configuration
	@Import({ 
			EmbeddedServletContainerAutoConfiguration.class,
			DispatcherServletAutoConfiguration.class,
			ServerPropertiesAutoConfiguration.class, 
			WebMvcAutoConfiguration.class,
			HttpMessageConvertersAutoConfiguration.class,
			PropertyPlaceholderAutoConfiguration.class
			})
	
	@ComponentScan("com.watsonas")
	
	public static class NonAutoConfigurationSampleTomcatApplication {
		public static void main(String[] args) throws Exception {
			SpringApplication app = new SpringApplication(HomeApplication.class);
			app.run(args);
		}
	}

	@Test
	public void testHome() throws Exception {
		/*
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
				"https://localhost:" + this.port + "/getById/95?id2=55", String.class );
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		assertEquals("GET Response : 95 55", entity.getBody());
		*/
	}

}
