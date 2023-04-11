package com.portal.api.message;

import com.portal.api.dto.CarPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducerMessage {

    private final KafkaTemplate<String, CarPostDTO> kafkaTemplate;

    private final String KAFKA_TOPIC = "car-post-topic";

    public void sendMessage(CarPostDTO carPostDTO) {
        kafkaTemplate.send(KAFKA_TOPIC, carPostDTO);
    }
}
