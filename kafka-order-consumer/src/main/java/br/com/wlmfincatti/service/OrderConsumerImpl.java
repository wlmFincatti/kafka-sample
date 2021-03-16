package br.com.wlmfincatti.service;

import br.com.wlmfincatti.Order;
import br.com.wlmfincatti.configuration.OrderConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class OrderConsumerImpl implements OrderConsumer {

    private CountDownLatch countDownLatch;
    private KafkaConsumer<String, Order> consumer;
    private static final String TOPIC = "order";

    public OrderConsumerImpl(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            consumer = new KafkaConsumer<>(OrderConfig.getProps());
            consumer.subscribe(Collections.singletonList(TOPIC));

            while (true) {
                consumer.poll(Duration.ofMillis(100))
                        .forEach(record ->
                                log.info("key: {} recordData: {} offset: {} partition: {} ",
                                        record.key(),
                                        record.value(),
                                        record.offset(),
                                        record.partition())

                        );
                consumer.commitSync();
            }
        } catch (WakeupException ex) {
            log.info("exiting consumer");
        } finally {
            log.info("closing consumer");
            consumer.close();
            countDownLatch.countDown();
        }
    }

    @Override
    public void shutdown() {
        consumer.wakeup();
    }
}
