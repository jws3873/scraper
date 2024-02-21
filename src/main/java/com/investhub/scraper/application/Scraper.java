package com.investhub.scraper.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Scraper {

    public Connection getConnection(String url){
        return Jsoup.connect(url);
    }

    public String getBlindTime(Document document) throws JsonProcessingException {
        Elements scriptElements = document.select("script[type=application/ld+json]");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(scriptElements.html());
        for (JsonNode node : jsonNode) {
            if (node.has("dateCreated")){
                return node.get("dateCreated").asText();
            }
        }
        return "";
    }

    // 조회수 숫자추출 메서드
    public String extractNumber(String input) {
        int number;
        try{
            number = Integer.parseInt(input.replaceAll("[^0-9]",""));
        }catch (NumberFormatException e){
            return "";
        }
        if(input.toUpperCase().contains("K")){
            return String.valueOf(number*1000);
        }
        return String.valueOf(number);
    }
}
