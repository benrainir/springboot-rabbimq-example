package com.globoplay.consumer.service;

import com.globoplay.consumer.constant.StatusConstant;
import com.globoplay.consumer.dto.SubscriptionDto;
import com.globoplay.consumer.entity.EventHistory;
import com.globoplay.consumer.entity.Status;
import com.globoplay.consumer.entity.Subscription;
import com.globoplay.consumer.repository.EventHistoryRepository;
import com.globoplay.consumer.repository.StatusRepository;
import com.globoplay.consumer.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.Serializable;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private EventHistoryRepository eventRepository;

    public Subscription purchase(SubscriptionDto subscriptionDto) {
        Subscription subscription =  subscriptionRepository.findById(subscriptionDto.subscription);
        Status statusEnabled =  statusRepository.findById(StatusConstant.ENABLED);

        if (subscription == null) {
            subscription = new Subscription(subscriptionDto.subscription);
            subscriptionRepository.save(subscription);
        }
        subscription.setStatus(statusEnabled);
        subscriptionRepository.save(subscription);

        addSubscriptionEvent(subscription, subscriptionDto.notification_type);

        return subscription;
    }

    public Subscription restart(SubscriptionDto subscriptionDto) throws Exception {
        Subscription subscription =  subscriptionRepository.findById(subscriptionDto.subscription);

        if (subscription == null) {
            throw new Exception("Subscription not found");
        }

        Status statusEnabled =  statusRepository.findById(StatusConstant.ENABLED);
        subscription.setStatus(statusEnabled);
        subscriptionRepository.save(subscription);

        addSubscriptionEvent(subscription, subscriptionDto.notification_type);

        return subscription;

    }

    public Subscription cancel(SubscriptionDto subscriptionDto) throws Exception {
        Subscription subscription =  subscriptionRepository.findById(subscriptionDto.subscription);

        if (subscription == null) {
            throw new Exception("Subscription not found");
        }

        Status statusDisabled =  statusRepository.findById(StatusConstant.DISABLED);
        subscription.setStatus(statusDisabled);
        subscriptionRepository.save(subscription);

        addSubscriptionEvent(subscription, subscriptionDto.notification_type);

        return subscription;
    }

    private void addSubscriptionEvent(Subscription subscription , String notificationType ) {
        EventHistory event = new EventHistory(subscription, notificationType);
        eventRepository.save(event);
    }

}
