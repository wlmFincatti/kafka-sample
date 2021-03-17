package br.com.wlmfincatti.configuration;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConfig {

    private static Properties properties = new Properties();

    public static Properties getProps() {
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "app-producer1");
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, Boolean.toString(true));
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, Integer.toString(5));
        properties.put(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32 * 1024));
        properties.put(ProducerConfig.LINGER_MS_CONFIG, Integer.toString(20));
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        properties.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        properties.put(KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS, Boolean.toString(false));
        return properties;
    }
}
