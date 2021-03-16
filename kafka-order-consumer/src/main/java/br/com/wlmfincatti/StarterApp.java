package br.com.wlmfincatti;

import br.com.wlmfincatti.service.OrderConsumer;
import br.com.wlmfincatti.service.OrderConsumerImpl;
import br.com.wlmfincatti.service.OrderConsumerService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class StarterApp {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        OrderConsumer order = new OrderConsumerImpl(countDownLatch);

        new OrderConsumerService(1)
                .executeThread(countDownLatch, order);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("error: {}", e.getMessage());
        } finally {
            log.info("application exiting");
        }
    }
}
