package com.investhub.scraper;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@Log4j2
@EnableAsync
@EnableScheduling
public class ScraperApplication {

    public static void main(String[] args) {SpringApplication.run(ScraperApplication.class,args);}

}
