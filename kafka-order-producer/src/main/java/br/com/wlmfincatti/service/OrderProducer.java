package br.com.wlmfincatti.service;

import br.com.wlmfincatti.Order;
import br.com.wlmfincatti.configuration.OrderConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Random;

@Slf4j
public class OrderProducer {

    private static final String TOPIC = "order";
    private KafkaProducer<String, Order> producer = new KafkaProducer<>(OrderConfig.getProps());

    public void send() {
        try {
            for (int i = 0; i < 1000; i++) {
                Order orderBuild = getOrder(i);
                producer.send(new ProducerRecord<>(TOPIC, "key".concat(Integer.toString(i)), orderBuild),
                        (recordMetadata, ex) -> {
                            if (ex != null) {
                                logError(ex);
                            }
                            log.info("record saved in offset: {} partition: {} ",
                                    recordMetadata.offset(),
                                    recordMetadata.partition());
                        });
            }
        } catch (RuntimeException ex) {
            logError(ex);
        } finally {
            log.info("producer closing");
            producer.close();
        }
    }

    private Order getOrder(int i) {
        return Order.newBuilder()
                .setName("order")
                .setPrice(10.0)
                .setQuantity(1)
                .build();
    }


    private void logError(Exception ex) {
        log.error("Error produer: {}", ex.getMessage());
    }
}
