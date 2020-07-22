package ru.dvkombarov.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
