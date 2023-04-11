package com.analytics.data.message;

import com.analytics.data.dto.CarPostDTO;
import com.analytics.data.service.PostAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumerMessage {
    private final Logger LOG = LoggerFactory.getLogger(KafkaConsumerMessage.class);
    private final PostAnalyticsService postAnalyticsService;

    @KafkaListener(topics = "car-post-topic", groupId = "analytics-posts-group")
    public void listening(@Payload CarPostDTO carPostDTO) {
        LOG.info("ANALYTICS DATA - Received Car Post information: {}", carPostDTO);
        this.postAnalyticsService.saveDataAnalytics(carPostDTO);
    }

}
