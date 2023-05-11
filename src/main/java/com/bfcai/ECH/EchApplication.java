package com.bfcai.ECH;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@RequiredArgsConstructor
public class EchApplication implements CommandLineRunner {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(EchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
