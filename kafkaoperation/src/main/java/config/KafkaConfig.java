package config;

import utils.PropertiesUtil;

import java.util.Properties;

public class KafkaConfig {

    public static Properties getProperties(){
        Properties properties=new Properties();
        properties.put("bootstrap.servers", PropertiesUtil.getProperty(Constants.KafkaConfigFileName, Constants.KAFKA_BOOTSTRAP_SERVERS));
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        return properties;
    }
}
