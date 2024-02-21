package com.investhub.scraper.application;

import com.investhub.scraper.Entity.DomainVo;
import com.investhub.scraper.config.DomainConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainService {

    @Autowired
    private DomainConfiguration domainConfiguration;

    public List<DomainVo> getDomains() {
        return domainConfiguration.getDomains();
    }
}
