package com.investhub.scraper.config;

import com.investhub.scraper.Entity.DomainVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "my-config")
public class DomainConfiguration {
    private List<DomainVo> domains;
}
