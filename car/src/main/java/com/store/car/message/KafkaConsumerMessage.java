package com.store.car.message;

import com.store.car.dto.CarPostDTO;
import com.store.car.service.CarPostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumerMessage {
    private final Logger LOG = LoggerFactory.getLogger(KafkaConsumerMessage.class);
    private final CarPostService carPostService;

    @KafkaListener(topics = "car-post-topic", groupId = "store-posts-group")
    public void listening(CarPostDTO carPostDTO) {
        LOG.info("CAR STORE - Received Car Post information: {}", carPostDTO);
        this.carPostService.newPostDetails(carPostDTO);
    }

}
