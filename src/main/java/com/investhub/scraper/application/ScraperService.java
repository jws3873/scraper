package com.investhub.scraper.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.investhub.scraper.Entity.CommunityDto;
import com.investhub.scraper.Entity.DomainVo;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ScraperService extends Scraper {

    @Autowired
    private KafkaProducer producer;

    @Async
    public void scrape(DomainVo domain) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        log.info("Domain {} start", domain.getName());

        Connection connection = getConnection(domain.getUrl());

        // 웹페이지에서 html을 받아온다.
        Document document = connection.get();

        // 리스트목록 추출
        Elements boardList = document.select(domain.getPostTag());

        // 리스트 디테일 추출
        boardList.stream().forEach(board ->{

            CommunityDto communityDto = new CommunityDto();

            // 게시글 제목 추출
            setTitle(domain, communityDto, board);

            // 게시글 내용 링크추출
            setContenturl(domain, communityDto, board);

            // 게시물 세부내용 추출 (본문내용, 작성시간, 조회수, 댓글)
            setDetail(domain, communityDto);

            producer.send("test1",communityDto);
        });
        log.info("Domain {} end", domain.getName());
    }

    // 제목추출
    private void setTitle(DomainVo domainVo, CommunityDto communityDto, Element board) {
        communityDto.setTitle(board.select(domainVo.getTitleTag()).text());
    }

    // 본문 url추출
    public void setContenturl(DomainVo domain, CommunityDto communityDto, Element board) {
        try{
            URL url = new URL(domain.getUrl());

            // hreg속성 추출
            String href = board.select(domain.getTitleTag()).attr("href");

            href = href.replace("..","");

            if(!href.contains(url.getProtocol() )){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(url.getProtocol())
                        .append("://")
                        .append(url.getHost())
                        .append(href);
                communityDto.setContentUrl(stringBuilder.toString());
            }else{
                communityDto.setContentUrl(href);
            }

        }catch (MalformedURLException e){
            log.error("Error while getting Contenturl :", e);
        }
    }

    public void setDetail(DomainVo domainVo, CommunityDto communityDto) {
        try{
            Document document = getConnection(communityDto.getContentUrl()).get();

            // 본문내용 set
            Elements contentElement = document.select(domainVo.getContentTag());
            communityDto.setContentDetail(contentElement.text());

            // 게시글 작성시간 set
            String time;
            if(domainVo.getName().equals("blind")){
                time = getBlindTime(document);
            }
            else{
                Elements timeElement = document.select(domainVo.getDateTag());
                time = timeElement.text();
            }
            communityDto.setTime(time);

            // 조회수 set
            String veiwCnt = extractNumber(document.select(domainVo.getViewCntTag()).text());
            communityDto.setViewCnt(veiwCnt);

            // 댓글 set
            List<String> commentList = new ArrayList<>();
            Elements commentElements = document.select(domainVo.getCommentTag());
            commentElements.stream().forEach(comment->{
                commentList.add(comment.text());
            });
            communityDto.setCommentList(commentList);

        }catch (IOException e){
            log.error("Error while getting getContent :", e);
        }
    }
}