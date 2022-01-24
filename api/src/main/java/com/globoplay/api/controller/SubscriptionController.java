package com.globoplay.api.controller;

import com.globoplay.api.constant.NotificationTypeConstant;
import com.globoplay.api.dto.SubscriptionDto;
import com.globoplay.api.service.RabbitmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {

    @Autowired
    private RabbitmqService rabbitmqService;

    @PostMapping(value = "/notify")
    private ResponseEntity notify(@RequestBody SubscriptionDto subscriptionDto){
        String queueName = subscriptionDto.notification_type;

        if ( queueName.equals(NotificationTypeConstant.PURCHASED)   ||
                queueName.equals(NotificationTypeConstant.RESTARTED) ||
                queueName.equals(NotificationTypeConstant.CANCELED) ) {
            this.rabbitmqService.sendMessage(queueName, subscriptionDto);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
