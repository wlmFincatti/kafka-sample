package br.com.wlmfincatti.service;

import br.com.wlmfincatti.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderConsumer {

    @KafkaListener(topics = "${kafka.consumer.order.topic}", groupId = "${kafka.consumer.order.groupId}")
    public void listenGroupFoo(ConsumerRecord<String, Order> record) {
        log.info("key: {} recordData: {} offset: {} partition: {} ",
                record.key(),
                record.value(),
                record.offset(),
                record.partition());
    }
}
