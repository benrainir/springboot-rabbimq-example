package com.globoplay.api.service;

import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String queueName, Object message){
        try {
            Gson gson = new Gson();
            String messageJson = gson.toJson(message);
            this.rabbitTemplate.convertAndSend(queueName, messageJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}