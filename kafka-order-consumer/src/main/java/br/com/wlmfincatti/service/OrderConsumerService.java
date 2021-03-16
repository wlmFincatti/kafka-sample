package br.com.wlmfincatti.service;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class OrderConsumerService {

    private ExecutorService service;
    private Integer numberOfThreads;

    public OrderConsumerService(Integer numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        service = Executors.newFixedThreadPool(this.numberOfThreads);
    }

    public void executeThread(CountDownLatch countDownLatch, OrderConsumer order) {
        service.execute(order);
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            log.info("shutdown hook");
            order.shutdown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
               log.error("erro: {}", e.getMessage());
            }
            log.info("application closing");
        }
        ));
    }
}
