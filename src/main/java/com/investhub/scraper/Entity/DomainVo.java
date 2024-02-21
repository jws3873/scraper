package com.investhub.scraper.Entity;

import lombok.Data;

@Data
public class DomainVo {
    private String name;
    private String url;
    private String postTag;
    private String titleTag;
    private String contentTag;
    private String dateTag;
    private String viewCntTag;
    private String commentTag;
}
