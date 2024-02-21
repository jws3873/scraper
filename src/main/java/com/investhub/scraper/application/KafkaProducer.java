package com.investhub.scraper.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investhub.scraper.Entity.CommunityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void send(String topic, CommunityDto communityDto) {
        try{
            String jsonObject = objectMapper.writeValueAsString(communityDto);
            kafkaTemplate.send(topic, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
