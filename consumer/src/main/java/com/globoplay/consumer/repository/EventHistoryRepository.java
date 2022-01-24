package com.globoplay.consumer.repository;

import com.globoplay.consumer.entity.EventHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventHistoryRepository extends JpaRepository<EventHistory, Long> {
    EventHistory findById(long id);
}
