package com.example.Sub;


import org.ldaptive.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.ldaptive.BindConnectionInitializer;
import org.ldaptive.Credential;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.ConnectionConfig;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

public class SubApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubApplication.class, args);
	}

}
