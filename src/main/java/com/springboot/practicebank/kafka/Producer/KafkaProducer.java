package com.springboot.practicebank.kafka.Producer;

import com.springboot.practicebank.dto.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaProducer  {

    private final NewTopic topic;
    private final KafkaTemplate<String, TransactionDto> kafkaTemplate;


    public void sendMessage(TransactionDto transactionDto) {
        log.info(String.format("Producer Event -> %s", transactionDto.toString()));

        Message<String> message = MessageBuilder
                .withPayload(transactionDto.toString())
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();

        kafkaTemplate.send(message);
    }

}
