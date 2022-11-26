package com.bfcai.ECH;

import com.bfcai.ECH.Entity.Patient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
