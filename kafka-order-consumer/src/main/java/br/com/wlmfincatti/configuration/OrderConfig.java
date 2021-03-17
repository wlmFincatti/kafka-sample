package br.com.wlmfincatti.configuration;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConfig {

    private static Properties properties = new Properties();

    public static Properties getProps() {
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "app-order-consumer");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "app1");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, Boolean.toString(false));
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,Integer.toString(100));
        properties.put(KafkaAvroDeserializerConfig.AUTO_REGISTER_SCHEMAS, Boolean.toString(false));
        properties.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        return properties;
    }

}
