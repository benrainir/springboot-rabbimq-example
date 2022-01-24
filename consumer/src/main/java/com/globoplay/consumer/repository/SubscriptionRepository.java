package com.globoplay.consumer.repository;

import com.globoplay.consumer.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findById(String id);
}
