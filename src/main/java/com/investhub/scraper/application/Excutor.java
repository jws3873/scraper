package com.investhub.scraper.application;

import com.investhub.scraper.Entity.DomainVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class Excutor {

    @Autowired
    private ScraperService scraperService;

    @Autowired
    private DomainService domainService;

    @Scheduled(fixedDelay = 120000) // 2minute
    public void work() throws IOException {
        List<DomainVo> domains = domainService.getDomains();

        for (DomainVo domain : domains) {
            scraperService.scrape(domain);
        }
    }
}
