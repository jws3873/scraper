package com.investhub.scraper;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class ScraperApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ScraperApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
    @Override
    public void run(String... args) throws Exception {
        log.info("scraper module Main");
    }

}
