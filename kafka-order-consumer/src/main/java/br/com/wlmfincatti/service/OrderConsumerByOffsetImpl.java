package br.com.wlmfincatti.service;

import br.com.wlmfincatti.Order;
import br.com.wlmfincatti.configuration.OrderConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class OrderConsumerByOffsetImpl implements OrderConsumer {

    private KafkaConsumer<String, Order> consumer;
    private static final String TOPIC = "order";
    private Integer partition;
    private TopicPartition topicPartition;
    private Integer offsetStart;
    private CountDownLatch countDownLatch;

    public OrderConsumerByOffsetImpl(Integer partition, Integer offsetStart, CountDownLatch countDownLatch) {
        this.partition = partition;
        this.offsetStart = offsetStart;
        this.topicPartition = new TopicPartition(TOPIC, this.partition);
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            consumer = new KafkaConsumer<>(OrderConfig.getProps());

            consumer.assign(Collections.singletonList(topicPartition));

            consumer.seek(topicPartition, offsetStart);

            consumer.poll(Duration.ofSeconds(1))
                    .forEach(record ->
                            log.info("key: {} recordData: {} offset: {} partition: {} ",
                                    record.key(),
                                    record.value(),
                                    record.offset(),
                                    record.partition())

                    );
        } catch (RuntimeException ex) {
            if (ex instanceof WakeupException) {
                log.info("exiting consumer");
            }
            log.error("error: {}", ex.getMessage());
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
