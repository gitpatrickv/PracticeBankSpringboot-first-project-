package com.springboot.practicebank.kafka.Consumer;

import com.springboot.practicebank.kafka.Repository.KafkaRepository;
import com.springboot.practicebank.kafka.entity.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final KafkaRepository kafkaRepository;
    @KafkaListener(topics = "bank_topic", groupId = "bank")
    public void consume(String event){

        log.info(String.format("Consumer Event Listener -> %s", event));

        Event saveEvent = new Event();
        saveEvent.setEventData(event);
        kafkaRepository.save(saveEvent);
    }


}
