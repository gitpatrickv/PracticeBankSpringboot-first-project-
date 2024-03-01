package com.springboot.practicebank.kafka.Repository;

import com.springboot.practicebank.kafka.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KafkaRepository extends JpaRepository<Event, Long> {
}
