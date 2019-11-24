package com.vane.pia;

import com.vane.pia.configuration.AppConfig;
import com.vane.pia.configuration.DbConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
	AppConfig.class,
	DbConfig.class
})
public class PiaApp {

	public static void main(String[] args) {
		SpringApplication.run(PiaApp.class, args);
		//SpringApplication.run(PiaApp.class, "--debug");
	}

}
