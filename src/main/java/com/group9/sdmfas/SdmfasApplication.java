package com.group9.sdmfas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SdmfasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdmfasApplication.class, args);
        System.out.println("\n=== SDMFAS is running ===");
        System.out.println("Open http://localhost:8080 in your browser\n");
    }
}
