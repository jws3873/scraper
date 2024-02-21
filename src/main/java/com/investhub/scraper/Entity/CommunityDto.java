package com.investhub.scraper.Entity;

import lombok.Data;

import java.util.List;

@Data
public class CommunityDto {
    private String title;
    private String contentUrl;
    private String contentDetail;
    private String time;
    private String viewCnt;
    private List<String> commentList;
}
