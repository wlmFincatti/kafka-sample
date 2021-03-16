package br.com.wlmfincatti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaOrderConsumerSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaOrderConsumerSpringApplication.class, args);
	}

}
