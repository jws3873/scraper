package com.investhub.scraper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 코어 스레드 풀 크기 설정
        executor.setMaxPoolSize(10); // 최대 스레드 풀 크기 설정
        executor.setQueueCapacity(100); // 작업 큐 용량 설정
        executor.setThreadNamePrefix("AsyncThread-"); // 스레드 이름 접두사 설정

        executor.initialize();
        return executor;
    }
}
