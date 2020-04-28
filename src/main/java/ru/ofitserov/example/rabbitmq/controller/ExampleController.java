package ru.ofitserov.example.rabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ofitserov.example.rabbitmq.rabbit.Const;

@Slf4j
@RestController("/")
public class ExampleController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ConnectionFactory connectionFactory;

    @PostMapping("sentMessage")
    public ResponseEntity sentMessage(@RequestParam(defaultValue = "Hello from RabbitMQ!") String message) {
        log.debug("message = {}", message);

        rabbitTemplate.convertAndSend(Const.TOPIC_EXCHANGE_NAME, "*", message);

        log.info("message = {} sent!", message);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("logInfo")
    public ResponseEntity logInfo() {
        log.debug("Autowired beans:");
        log.debug("rabbitTemplate = {}", rabbitTemplate);
        log.debug("connectionFactory = {}", connectionFactory);
        log.debug("connectionFactory.username = {}", connectionFactory.getUsername());

        return new ResponseEntity(HttpStatus.OK);
    }
}
